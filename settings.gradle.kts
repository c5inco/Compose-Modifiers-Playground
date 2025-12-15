pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }
}
rootProject.name = "ModifiersPlayground"

include(":shared")
include(":desktopApp")
include(":jsApp")
include(":ideaPlugin")
