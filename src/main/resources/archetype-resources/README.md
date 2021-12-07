# Project based on Web Project Archetype

Hello world project


Summary features
-------

The application will start using the port 8080.

For tests purpose, it is defined the test profile, that you can find in src/test/resources.
All tests that used the test profile will use an embedded h2 database, initialized accordingly with the data.sql and schema.sql files.
Inside the test resource is also present the jmeter configuration valid for performance tests.

Other stuffs are:
- A
- B
- C


Usage
-------

Istruction 1, istruction 2, etc...

Skaffold istruction
-------

Aftering installing Minikube and Skaffold, start them using the following commands:
```
minikube start --profile custom
minikube --profile custom addons enable ingress
skaffold config set --global local-cluster true
eval $(minikube -p custom docker-env)
```

Pay attention: using Minikube and the local Docker registry, the image pull policy must be _IfNotPresent_.

Using the default configuration you can find into the _config/deployment.yml_ file, the service will be exposed with the port 80.

License
-------

  Copyright (C) 2022
 
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
