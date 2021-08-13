buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/google/") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${BuildConfig.gradle_version}")
        classpath("${Kotlin.plugin}")
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/google/") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}