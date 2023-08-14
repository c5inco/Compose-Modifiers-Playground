import org.jetbrains.compose.desktop.application.dsl.TargetFormat

fun properties(key: String) = providers.gradleProperty(key)

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("org.jetbrains.intellij") version "1.14.1"
}
dependencies {
    implementation(project(mapOf("path" to ":")))
}

kotlin {
    jvm {}
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.linux_x64)
                implementation(compose.desktop.windows_x64)
                implementation(compose.desktop.macos_x64)
                implementation(compose.desktop.macos_arm64)
                implementation(project(":shared"))
            }
        }
    }
}

intellij {
    pluginName = properties("pluginName")
    version = properties("platformVersion")
    type = properties("platformType")
}

tasks {
    patchPluginXml {
        version = properties("pluginVersion")
        sinceBuild = properties("pluginSinceBuild")
        untilBuild = properties("pluginUntilBuild")

        changeNotes.set("""
            Bumped IntelliJ plugin versions (min: 223, max: 233.*), Compose Desktop dependencies to 1.4.0
        """)
    }
}