group = "com.example"
version = "1.0.0"
plugins {
    id("java")
    id("java-gradle-plugin")

}
gradlePlugin {
    plugins {
        create("my-binary-plugin") {
            id = "com.example.my-binary-plugin"
            implementationClass = "FileSizeReporterPlugin"
        }
    }
}
