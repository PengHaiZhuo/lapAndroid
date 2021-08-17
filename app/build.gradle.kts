import kotlin.collections.*

plugins {
    id("com.android.application")
    kotlin("android")//id("kotlin-android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = BuildConfig.compileSdk
    buildToolsVersion = BuildConfig.buildToolsVersion

    defaultConfig {
        applicationId = BuildConfig.applicationId
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName
        testInstrumentationRunner = BuildConfig.testInstrumentationRunner

        ndk {
            //不配置则默认构建并打包所有可用的ABI
            //gradle版本-> abiFilters 'x86_64','armeabi-v7a','arm64-v8a'
            abiFilters.addAll(arrayListOf("x86_64","armeabi-v7a", "arm64-v8a"))
        }
    }

    base {
        //Android Studio 默认通过构建类型名称添加 versionNameSuffix
        archivesBaseName = "EnjoyAndroid(${BuildConfig.versionName})"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            versionNameSuffix = ""
        }
        lint {
            isCheckReleaseBuilds = false
            isAbortOnError = false
        }
    }
    packagingOptions {
        resources.excludes += "META-INF/gradle/incremental.annotation.processors"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.getName())
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(mapOf("path" to ":common")))

    implementation(Kotlin.Coroutines.core)
    implementation(Kotlin.Coroutines.android)

    implementation(AndroidX.appcompat)
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.constraintlayout)
    implementation(AndroidX.cardview)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.activityKtx)
    implementation(AndroidX.fragmentKtx)

    testImplementation(Testing.junit)
    androidTestImplementation(Testing.androidJunit)
    androidTestImplementation(Testing.espresso)

    //material包
    implementation(Google.material)

    //JetPack lifecycle
    implementation(AndroidX.Lifecycle.liveDataKtx)
    implementation(AndroidX.Lifecycle.viewModelKtx)
    implementation(AndroidX.Lifecycle.viewModelSavedState)
    implementation(AndroidX.Lifecycle.commonJava8)
    implementation(AndroidX.Lifecycle.service)
    implementation(AndroidX.Lifecycle.process)
    implementation(AndroidX.Lifecycle.runtimeKtx)

    //JetPack navigation
    implementation(AndroidX.Navigation.fragmentKtx)
    implementation(AndroidX.Navigation.uiKtx)

    //JetPack Room
    implementation(AndroidX.Room.runtime)
    kapt(AndroidX.Room.compiler)
    implementation(AndroidX.Room.ktx)
    implementation(AndroidX.Room.rxjava3)
    implementation(AndroidX.Room.guava)

    implementation(Google.guava_conflict)

    //viewPager
    implementation(AndroidX.ViewPager.viewpager2)
    implementation(AndroidX.ViewPager.viewpager)

    //retrofit2
    implementation(ThirdPart.Retrofit.retrofit)
    implementation(ThirdPart.Retrofit.convertGson)
    implementation(ThirdPart.Retrofit.adapterRxjava2)
    implementation(ThirdPart.Retrofit.convertScalars)

    //okhttp
    implementation(ThirdPart.OkHttp.okhttp)
    implementation(ThirdPart.OkHttp.urlConnection)
    implementation(ThirdPart.OkHttp.loggingInterceptor)

    //glide
    implementation(ThirdPart.Glide.glide)
    implementation(ThirdPart.Glide.compiler)

    //rxjava
    implementation(ThirdPart.rxjava2)
    implementation(ThirdPart.rxandroid)

    implementation(ThirdPart.MaterialDialogs.core)
    implementation(ThirdPart.MaterialDialogs.input)
    implementation(ThirdPart.MaterialDialogs.color)
    implementation(ThirdPart.MaterialDialogs.files)
    implementation(ThirdPart.MaterialDialogs.datetime)
    implementation(ThirdPart.MaterialDialogs.bottomsheets)
    implementation(ThirdPart.MaterialDialogs.lifecycle)

    implementation(ThirdPart.baseRecycleViewHelper)
    //mkkv
    implementation(ThirdPart.mmkv)
    //blankj工具集
    implementation(ThirdPart.utilcodex)
    //unPeekLivedata
    implementation(ThirdPart.unPeekLivedata)
    //bugly
    implementation(ThirdPart.bugly)
    //loadSir
    implementation(ThirdPart.loadSir)
    //标题栏工具
    implementation(ThirdPart.immersionbar)
    implementation(ThirdPart.immersionbarKtx)
    //运行时权限请求
    implementation(ThirdPart.rxPermission)
    //蓝牙工具
    implementation(ThirdPart.rxandroidble)
    implementation(ThirdPart.rxjavaReplayingShare)
    //ARouter路由
    implementation(ThirdPart.aRouter)
    kapt(ThirdPart.aRouterCompiler)
}