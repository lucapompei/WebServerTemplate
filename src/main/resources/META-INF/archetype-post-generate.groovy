import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// the path where the project got generated
Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

// the properties available to the archetype
Properties properties = request.properties

// connectionType is either ftp or sftp
String withSecurity = properties.get("withSecurity")

// the Java package of the generated project, e.g. com.acme
String packageName = properties.get("package")

// convert it into a path, e.g. com/acme
String packagePath = packageName.replace(".", "/")

if (withSecurity != 'S') {
    // delete the FTP file
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/JwtAuthenticationFilter.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/JwtAuthorizationFilter.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/SecurityConfig.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/config/security/")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/constants/AuthConstants.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/dao/UserRepository.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/dao/")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/model/ApplicationUser.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/model/")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/service/ApplicationUserDetailsService.java")
    Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/utils/JwtUtils.java")
    Files.deleteIfExists projectPath.resolve("src/test/java/" + packagePath + "/TestDataSource.java")
}