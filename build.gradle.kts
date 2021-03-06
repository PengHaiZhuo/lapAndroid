buildscript {
    repositories {
        mavenCentral()
        google()
        /**
         * 使用gradlePluginPortal()允许您解析buildscript { }块中
         * 发布到插件门户（https://plugins.gradle.org/m2/）或 JCenter或 Maven Central的任何插件，而无需关心它的发布位置或定义多个存储库。
         */
        gradlePluginPortal()
        maven ("https://jitpack.io")
//        maven { setUrl("https://maven.aliyun.com/repository/public") }
//        maven { setUrl("https://maven.aliyun.com/repository/google/") }
//        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
    dependencies {
        classpath("${Gradle.plugin}")
        classpath("${Kotlin.plugin}")
        classpath("${AndroidX.Navigation.safeArgsGradlePlugin}")
        classpath("${AndroidX.Hilt.gradlePlugin}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven ("https://jitpack.io")
//        maven { setUrl("https://maven.aliyun.com/repository/public") }
//        maven { setUrl("https://maven.aliyun.com/repository/google/") }
//        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}