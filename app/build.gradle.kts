plugins {
    id("com.android.application")
    kotlin("android")//id("kotlin-android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs")
    id("dagger.hilt.android.plugin")
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
            abiFilters.addAll(arrayListOf("x86","x86_64", "armeabi-v7a", "arm64-v8a"))
        }

        javaCompileOptions{
            annotationProcessorOptions {
                //app/schemas目录中会生成记录，可以轻松跟踪数据库历时
                arguments["room.schemaLocation"] = "${projectDir}/schemas"
            }
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
        /*compose =true*/
        dataBinding = true
        viewBinding = true
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
    implementation(AndroidX.swiperefreshlayout)
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

    //hilt
    implementation(AndroidX.Hilt.common)
    kapt(AndroidX.Hilt.compiler)

    //viewPager
    implementation(AndroidX.ViewPager.viewpager2)
    implementation(AndroidX.ViewPager.viewpager)

    //camera
    implementation(AndroidX.Camera.camera2)
    implementation(AndroidX.Camera.core)
    implementation(AndroidX.Camera.lifecycle)
    implementation(AndroidX.Camera.view)

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
    implementation(ThirdPart.rxAndroid)

    //材料设计弹出框
    implementation(ThirdPart.MaterialDialogs.core)
    implementation(ThirdPart.MaterialDialogs.input)
    implementation(ThirdPart.MaterialDialogs.color)
    implementation(ThirdPart.MaterialDialogs.files)
    implementation(ThirdPart.MaterialDialogs.datetime)
    implementation(ThirdPart.MaterialDialogs.bottomSheets)
    implementation(ThirdPart.MaterialDialogs.lifecycle)

    //好用的列表适配器
    implementation(ThirdPart.baseRecycleViewHelper)
    //mkkv
    implementation(ThirdPart.mmkv)
    //blankj工具集
    implementation(ThirdPart.utilCodex)
    //unPeekLivedata
    implementation(ThirdPart.unPeekLivedata)
    //bugly
    implementation(ThirdPart.bugly)
    //loadSir
    implementation(ThirdPart.loadSir)
    //标题栏工具
    implementation(ThirdPart.immersionBar)
    implementation(ThirdPart.immersionBarKtx)
    //运行时权限请求
    implementation(ThirdPart.xxPermission)
    //蓝牙工具
    implementation(ThirdPart.rxAndroidBle)
    implementation(ThirdPart.rxjavaReplayingShare)
    //console
    implementation(ThirdPart.console)
    //扫码
    implementation(Google.barcode_scanning)
    //lottie
    implementation(ThirdPart.lottie)
    //ExoPlayer相关
    implementation(ThirdPart.ExoPlayer.core)
    implementation(ThirdPart.ExoPlayer.ui)
    implementation(ThirdPart.ExoPlayer.hls)
    //BaiduMap相关
    implementation(ThirdPart.BaiduMap.map)
    implementation(ThirdPart.BaiduMap.location)
}