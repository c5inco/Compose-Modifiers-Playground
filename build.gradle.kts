import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.intellij.tasks.PatchPluginXmlTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "0.6.5"
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.compose") version "0.3.0"
}

group = "me.c5inco"
version = "0.1.5"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.linux_x64)
    implementation(compose.desktop.windows_x64)
    implementation(compose.desktop.macos_x64)
    implementation(compose.desktop.macos_arm64)
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
            packageVersion = "1.0.5"
        }
    }
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.3.3"
    type = "IC"
}

tasks.getByName<PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("""
      Properly include native dependencies for Windows, Linux, and ARM macOS to avoid runtime crashes!
      <br>
      Bump support for 2021.1!
    """)
    sinceBuild("201.*")
    untilBuild("211.*")
}
