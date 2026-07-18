fun properties(key: String) = providers.gradleProperty(key)

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

version = properties("pluginVersion").get()

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    jvm("desktop")

    js(IR) {
        browser()
    }

    wasmJs {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.ui)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.components.resources)
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
            }
        }
        val jsMain by getting {
            kotlin.srcDir("src/webMain/kotlin")
        }
        val wasmJsMain by getting {
            kotlin.srcDir("src/webMain/kotlin")
        }
    }
}
