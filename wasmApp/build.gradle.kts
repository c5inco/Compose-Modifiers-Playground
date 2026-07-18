plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
kotlin {
    wasmJs {
        browser()
        binaries.executable()
    }
    sourceSets {
        val wasmJsMain by getting {
            kotlin.srcDir("../jsApp/src/webMain/kotlin")
            resources.srcDir("../jsApp/src/webMain/resources")
            dependencies {
                implementation(project(":shared"))
            }
            resources.srcDir("../shared/src/commonMain/composeResources")
        }
    }
}
