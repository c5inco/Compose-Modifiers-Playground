plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
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
            resources.srcDir("../shared/src/commonMain/resources")
        }
    }
}

compose.experimental {
    web.application {}
}