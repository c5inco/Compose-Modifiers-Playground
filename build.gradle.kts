import org.jetbrains.compose.desktop.application.dsl.TargetFormat

fun properties(key: String) = providers.gradleProperty(key)

plugins {
    id("org.jetbrains.intellij") version "1.14.1"
    id("org.jetbrains.changelog") version "2.1.0"
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.compose") version "1.4.0"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.linux_x64)
    implementation(compose.desktop.windows_x64)
    implementation(compose.desktop.macos_x64)
    implementation(compose.desktop.macos_arm64)
    implementation(compose.materialIconsExtended)
}

compose.desktop {
    application {
        mainClass = "com.c5inco.modifiers.MainKt"
        nativeDistributions {
            macOS {
                iconFile.set(project.file("src/main/resources").resolve("META-INF/macosicon.icns"))
            }
            targetFormats(TargetFormat.Dmg)
            packageName = properties("pluginGroup").get()
            packageVersion = properties("pluginVersion").get()
        }
    }
}

kotlin {
    jvmToolchain(17)
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    pluginName = properties("pluginName")
    version = properties("platformVersion")
    type = properties("platformType")
}

tasks {
    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }
    patchPluginXml {
        version = properties("pluginVersion")
        sinceBuild = properties("pluginSinceBuild")
        untilBuild = properties("pluginUntilBuild")

        changeNotes.set("""
            Bumped IntelliJ plugin versions (min: 223, max: 233.*), Compose Desktop dependencies to 1.4.0
        """)
    }
}