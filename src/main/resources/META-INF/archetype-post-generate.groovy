import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// the path where the project got generated
Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

// the properties available to the archetype
Properties properties = request.properties

// the withSecurity property
String withSecurity = properties.get("withSecurity")

// the withKubernetes property
String withKubernetes = properties.get("withKubernetes")

// the Java package of the generated project, e.g. com.acme
String packageName = properties.get("package")

// convert it into a path, e.g. com/acme
String packagePath = packageName.replace(".", "/")

if (withSecurity != 'Y') {
    // delete files not used
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/JwtAuthenticationFilter.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/JwtAuthorizationFilter.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/SecurityConfig.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/package-info.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/constants/AuthConstants.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/repositories/UserRepository.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/entities/ApplicationUser.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/services/impl/UserDetailsService.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/utils/JwtUtils.java")
    Files.deleteIfExists projectPath.resolve("src/test/java/" + packagePath + "/TestDataSource.java")
}

if (withKubernetes != 'Y') {
    // delete files not used
    Files.deleteIfExists projectPath.resolve("config/interpolate.sh")
    Files.deleteIfExists projectPath.resolve("config/interpolate.sh")
    Files.deleteIfExists projectPath.resolve("config/deployment.yml")
    Files.deleteIfExists projectPath.resolve("config/env/dev.env")
    Files.deleteIfExists projectPath.resolve("config/env/master.env")
    Files.deleteIfExists projectPath.resolve("config/env/")
    Files.deleteIfExists projectPath.resolve("config/")
    Files.deleteIfExists projectPath.resolve("Dockerfile")
    Files.deleteIfExists projectPath.resolve("skaffold.yaml")
}

if (withSystemDemon != 'Y') {
    // delete files not used
    Files.deleteIfExists projectPath.resolve("backend.service")
}