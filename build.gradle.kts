plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.9.21" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21" apply false
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.compose") version "1.5.11" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "1.9.21" apply false
    id("app.cash.sqldelight") version "2.0.1" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}