plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.20")
    implementation(gradleApi())
    implementation(localGroovy())
}