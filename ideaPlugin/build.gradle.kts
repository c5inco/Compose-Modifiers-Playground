fun properties(key: String) = providers.gradleProperty(key)

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrainsIntellij)
    alias(libs.plugins.jetbrainsChangelog)
}

version = properties("pluginVersion").get()

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
}

intellij {
    pluginName = properties("pluginName").get()
    version = libs.versions.intellij.platform.get()
    type = properties("platformType").get()
}

tasks {
    buildSearchableOptions {
        enabled = false
    }
    patchPluginXml {
        version = properties("pluginVersion").get()
        sinceBuild = properties("pluginSinceBuild").get()
        untilBuild = properties("pluginUntilBuild").get()

    changeNotes.set("""
        - Bumped IntelliJ plugin versions (min: 241, max: 263.*)
        - Compose Desktop dependencies to 1.6.11
        - Exclude coroutines for IJ plugin to avoid class loader conflicts with Compose
    """)
    }
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}