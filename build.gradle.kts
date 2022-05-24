import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "1.3.0"
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.1"
}

group = "me.c5inco"
version = "0.1.13"

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

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

compose.desktop {
    application {
        mainClass = "com.c5inco.modifiers.MainKt"
        nativeDistributions {
            macOS {
                iconFile.set(project.file("src/main/resources").resolve("META-INF/macosicon.icns"))
            }
            targetFormats(TargetFormat.Dmg)
            packageName = "com.c5inco.modifiers"
            packageVersion = "1.0.13"
        }
    }
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.1.3")
    type.set("IC")
}

tasks.getByName<PatchPluginXmlTask>("patchPluginXml") {
    changeNotes.set("""
      - Bump supported IntelliJ plugin version
    """)
    sinceBuild.set("201.*")
    untilBuild.set("231.*")
}
