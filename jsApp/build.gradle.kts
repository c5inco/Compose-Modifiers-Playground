plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js(IR) {
        browser() {
            commonWebpackConfig {
                configDirectory = file(".")
            }
        }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting  {
            dependencies {
                implementation(project(":shared"))
            }
            resources.srcDir("../shared/src/commonMain/composeResources")
        }
    }
}