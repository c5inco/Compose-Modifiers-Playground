fun properties(key: String) = providers.gradleProperty(key)

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.intellijPlatform)
    alias(libs.plugins.jetbrainsChangelog)
}

version = properties("pluginVersion").get()

repositories {
    mavenCentral()
    
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation(compose.desktop.linux_x64) {
        exclude(group = "org.jetbrains.kotlinx")
    }
    implementation(compose.desktop.windows_x64) {
        exclude(group = "org.jetbrains.kotlinx")
    }
    implementation(compose.desktop.macos_x64) {
        exclude(group = "org.jetbrains.kotlinx")
    }
    implementation(compose.desktop.macos_arm64) {
        exclude(group = "org.jetbrains.kotlinx")
    }
    implementation(project(":shared")) {
        exclude(group = "org.jetbrains.kotlinx")
    }

    intellijPlatform {
        intellijIdeaCommunity(libs.versions.intellij.ide)
    }
}

intellijPlatform {
    pluginConfiguration {
        name = properties("pluginName").get()
        version = properties("pluginVersion").get()
        
        ideaVersion {
            sinceBuild = properties("pluginSinceBuild").get()
            untilBuild = properties("pluginUntilBuild").get()
        }
        
        changeNotes = """
            <ul>
                <li>Build against IntelliJ IDEA Community 2025.1 and verify through IntelliJ IDEA 2026.2</li>
                <li>Upgrade Compose Desktop dependencies to 1.8.2</li>
                <li>Exclude coroutines for IJ plugin to avoid class loader conflicts with Compose</li>
            </ul>
        """
    }
    
    buildSearchableOptions = false

    pluginVerification {
        ides {
            current()
            create("IU", "2026.2")
        }
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

// Skip tasks that require JBR download
tasks.named("buildSearchableOptions") {
    enabled = false
}

tasks.named("jarSearchableOptions") {
    enabled = false
}
