plugins {
    id("com.android.library")
    id("kotlin-android") //kotlin("android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}


android {
    compileSdk = BuildConfig.compileSdk
    buildToolsVersion = BuildConfig.buildToolsVersion

    defaultConfig {
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding =true
        viewBinding = true
    }
    buildTypes {
        lint {
            isCheckReleaseBuilds = false
            isAbortOnError = false
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(AndroidX.appcompat)
    implementation (AndroidX.constraintlayout)
    implementation (AndroidX.cardview)
    implementation (AndroidX.recyclerView)
    implementation (AndroidX.coreKtx)
    implementation (AndroidX.activityKtx)
    implementation(AndroidX.fragmentKtx)
    //material包
    implementation(Google.material)

    //JetPack navigation
    implementation(AndroidX.Navigation.fragmentKtx)
    implementation(AndroidX.Navigation.uiKtx)

    //JetPack lifecycle
    implementation(AndroidX.Lifecycle.liveDataKtx)
    implementation(AndroidX.Lifecycle.viewModelKtx)
    implementation(AndroidX.Lifecycle.viewModelSavedState)
    implementation(AndroidX.Lifecycle.commonJava8)
    implementation(AndroidX.Lifecycle.service)
    implementation(AndroidX.Lifecycle.process)
    implementation(AndroidX.Lifecycle.runtimeKtx)

    //JetPack Room
    /*implementation(AndroidX.Room.runtime)
    kapt(AndroidX.Room.compiler){
        exclude(group = "com.intellij",module = "annotations")
    }
    implementation(AndroidX.Room.ktx)
    implementation(AndroidX.Room.rxjava3)
    implementation(AndroidX.Room.guava)*/

    /*implementation(Google.guava_conflict)*/

    //viewPager
    implementation(AndroidX.ViewPager.viewpager2)
    implementation(AndroidX.ViewPager.viewpager)

    //retrofit2
    implementation (ThirdPart.Retrofit.retrofit)
    implementation (ThirdPart.Retrofit.convertGson)
    implementation (ThirdPart.Retrofit.adapterRxjava2)

    //okhttp
    implementation (ThirdPart.OkHttp.okhttp)
    implementation (ThirdPart.OkHttp.urlConnection)
    implementation (ThirdPart.OkHttp.loggingInterceptor)

    //glide
    implementation (ThirdPart.Glide.glide)
    implementation (ThirdPart.Glide.compiler)

    //rxjava
    implementation (ThirdPart.rxjava2)
    implementation (ThirdPart.rxAndroid)

    implementation(ThirdPart.MaterialDialogs.core)
    implementation(ThirdPart.MaterialDialogs.input)
    implementation(ThirdPart.MaterialDialogs.color)
    implementation(ThirdPart.MaterialDialogs.files)
    implementation(ThirdPart.MaterialDialogs.datetime)
    implementation(ThirdPart.MaterialDialogs.bottomSheets)
    implementation(ThirdPart.MaterialDialogs.lifecycle)

    //unPeekLivedata
    implementation(ThirdPart.unPeekLivedata)
    //标题栏工具
    implementation(ThirdPart.immersionBar)
    implementation(ThirdPart.immersionBarKtx)
}
