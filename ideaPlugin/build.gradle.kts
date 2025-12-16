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
        intellijIdea("2025.1")
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
                <li>Bumped IntelliJ plugin versions (min: 243, max: 263.*)</li>
                <li>Compose Desktop dependencies to 1.6.11</li>
                <li>Exclude coroutines for IJ plugin to avoid class loader conflicts with Compose</li>
            </ul>
        """
    }
    
    buildSearchableOptions = false
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