#!/usr/bin/env bash
#
# This script accept x paramenters and will interpolate the custom values from the passed variables inside the
# deployments file.

set -o errexit
set -o nounset

# Check if sed is installed
command -v sed >/dev/null 2>&1 || { echo "sed must be installed for this script! Aborting"; exit 1; }

__DIR=$(dirname "${0}")
# Load the environment-specific variables
ENV_FILE="${__DIR}/${ENVIRONMENT}"
set -a && . "${ENV_FILE}"
set +a

# Automatically get the folder where the source files must be placed.
SOURCE_DIR="${__DIR}"
# Check if the folder exists
if [ ! -d "${SOURCE_DIR}" ]; then echo "The ${SOURCE_DIR} folder does not exists. Aborting!"; exit 1; fi

# Enforce the creation and the emptiness of the destination folder where the interpolated files will be placed.
DEST_DIR="${__DIR}/interpolated-files"
rm -fr "${DEST_DIR}"
mkdir -p "${DEST_DIR}"

interpolate_value() {
    local KEY=${1}
    local VALUE=${2}
    local FILE_PATH=${3}
    # The VALUE is being escaped for allow sed to working fine and don't f**k up the substitution
    sed -i.bck "s|${KEY}|${VALUE//&/\\&}|g" "${FILE_PATH}"    
}

check_file() {
    if [ $(grep -Ec "{{[A-Z_]+}}" ${1}) -ne 0 ]; then
        echo "Missing interpolation in ${1}"
        grep -E "{{[A-Z_]+}}" ${1}
        exit 1
    fi
}

# Copy all the source files to DEST_DIR
cp -r "${SOURCE_DIR}"/*.yml "${DEST_DIR}"

echo "Start files interpolations..."
for filePath in "${DEST_DIR}"/*.yml; do
    echo "Interpolating ${filePath}"
    for variable_to_interpolate in `cat $ENV_FILE | awk -F= '{ print $1 }'`
    do
        interpolate_value "{{${variable_to_interpolate}}}" "${!variable_to_interpolate}" "${filePath}"
    done
    check_file "${filePath}"
done

rm -fr "${DEST_DIR}"/*.bck
echo "Finish files interpolations..."
