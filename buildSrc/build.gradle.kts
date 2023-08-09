import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())
//    implementation("com.android.tools.build:gradle:7.4.1")
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
}
