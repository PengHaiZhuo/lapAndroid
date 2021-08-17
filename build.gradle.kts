buildscript {
    repositories {
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/google/") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${BuildConfig.gradle_version}")
        classpath("${Kotlin.plugin}")
        classpath("${AndroidX.Navigation.safeArgsGradlePlugin}")
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/google/") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}