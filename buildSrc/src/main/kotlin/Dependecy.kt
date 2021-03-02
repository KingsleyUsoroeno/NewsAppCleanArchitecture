import Versions.glide
import Versions.hilt_view_models
import Versions.nav_version
import Versions.work_version

object Constants {
    const val compileSdkVersion = 30
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"
    const val buildToolsVersion = "30.0.3"
    const val applicationId = "com.techkingsley.newsappcleanarchitecture"
}

object Versions {
    const val assertJ = "3.15.0"
    const val coroutines = "1.3.5"
    const val androidXCoreVersion = "1.3.1"
    const val androidx_core_version = "1.3.1"
    const val androidx_test_version = "1.1.2"
    const val arch_testing_version = "2.1.0"
    const val appcompat_version = "1.2.0"

    const val constraint_layout_version = "2.0.4"
    const val coroutines_adapter_version = "0.9.2"
    const val coroutines_version = "1.3.7"
    const val espresso_core_version = "3.3.0"
    const val fragmentKtx_version = "1.3.0-alpha08"
    const val fragment_testing_version = "1.2.5"

    const val hilt_lifecycle_extensions_version = "1.0.0-alpha02"
    const val junit_version = "4.13"
    const val material_version = "1.3.0-beta01"

    const val moshi_version = "1.10.0"
    const val okhttp_version = "4.9.0"
    const val okhttp_logging_iterceptor_version = "4.9.0"
    const val retrofit_moshi_converter_version = "2.9.0"
    const val room_version = "2.2.6"
    const val nav_version = "2.3.3"
    const val retrofit_version = "2.9.0"

    const val timber_version = "4.7.1"
    const val viewModelKtx_version = "2.3.0-alpha07"
    const val kotlinVersion = "1.3.72"
    const val fragment_ktx = "1.3.0"
    const val life_cycle_live_data = "2.3.0"
    const val glide = "4.12.0"
    const val work_version = "2.5.0"
    const val hilt_version = "2.31.2-alpha"
    const val hilt_android_compiler = "2.31.2-alpha"
    const val hilt_view_models = "1.0.0-alpha01"
    const val javaxInjectVersion = "1"
}

object Libraries {
    const val androidXCore = "androidx.core:core-ktx:${Versions.androidx_core_version}"
    const val Junit = "junit:junit:${Versions.junit_version}"
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.life_cycle_live_data}"

    // Navigation
    const val fragmentNavigation = "androidx.navigation:navigation-fragment-ktx:$nav_version"
    const val fragmentUi = "androidx.navigation:navigation-ui-ktx:$nav_version"

    const val materialDesign = "com.google.android.material:material:${Versions.material_version}"
    const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"

    const val glideVersion = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glide"

    const val materialSearchView = "com.miguelcatalan:materialsearchview:1.4.0"

    const val workManager = "androidx.work:work-runtime-ktx:$work_version"

    const val hiltWorker = "androidx.hilt:hilt-work:1.0.0-alpha03"
    const val hiltWorkerCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha03"

    const val lifeCycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_android_compiler}"

    // DAGGER HILT AND VIEW MODEL
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hilt_view_models"
    const val hiltViewModelCompiler = "androidx.hilt:hilt-compiler:$hilt_view_models"

    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_version}"

    const val viewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx_version}"

    const val timberVersion = "com.jakewharton.timber:timber:${Versions.timber_version}"

    const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInjectVersion}"
}

object Network {
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val retrofitScalars =
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofit_version}"

    const val retrofitMoshi =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofit_moshi_converter_version}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi_version}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi_version}"

    const val retrofitCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutines_adapter_version}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_iterceptor_version}"

    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
}

object Cache {
    const val room = "androidx.room:room-runtime:${Versions.room_version}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room_version}"
    const val roomTesting = "androidx.room:room-testing:${Versions.room_version}"
    const val roomCoroutines = "androidx.room:room-ktx:${Versions.room_version}"
}