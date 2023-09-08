fun properties(key: String) = providers.gradleProperty(key)

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

version = properties("pluginVersion").get()

kotlin {
    jvm("desktop")

    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.ui)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
    }
}