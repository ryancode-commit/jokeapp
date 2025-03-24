import java.util.Properties

plugins{
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}


android {
    namespace = "com.ryancode.jokeapp"
    compileSdk = 34
    buildFeatures.dataBinding = true

    buildFeatures{
        buildConfig = true
        compose = true
    }


    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }


    viewBinding{
        enable   = true
    }

    defaultConfig {
        applicationId = "com.ryancode.jokeapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {
        debug{
        }
        release {
            isMinifyEnabled  = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.activity:activity:1.9.3")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.7")

    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${property("kotlin_version")}")
    implementation("androidx.annotation:annotation:1.9.1")

    // Bottom Navigation
    implementation("com.google.android.material:material:1.12.0")

    // Material Design
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.1.0")

    // OkHttp3
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation("com.google.code.gson:gson:2.11.0")

    // RxJava
    implementation("io.reactivex.rxjava2:rxjava:2.2.7")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("com.akexorcist:RoundCornerProgressBar:2.0.3")


    // Glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("jp.wasabeef:glide-transformations:4.1.0")

    // Scalable Size Unit
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    implementation("com.intuit.ssp:ssp-android:1.0.6")

    // PhotoView
    implementation("com.github.chrisbanes:PhotoView:2.3.0")

    // LoaderView
    implementation("com.elyeproj.libraries:loaderviewlibrary:2.0.0")

    // Carousel View
    implementation("com.synnapps:carouselview:0.1.4")

    // Ripple Effect
    implementation("com.balysv:material-ripple:1.0.2")
    implementation("com.github.traex.rippleeffect:library:1.3")

    // Compressor
    implementation("id.zelory:compressor:3.0.0") {
        exclude(module = "unspecified")
    }

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-alpha05")

    // WorkManager
    implementation("android.arch.work:work-runtime:1.0.1")

    // Currency Edit Text
    implementation("me.abhinay.input:currency-edittext:1.1")

    // Number Picker
    implementation("com.shawnlin:number-picker:2.4.6")

    // Shimmer Loading
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Custom Toast
    implementation("com.github.GrenderG:Toasty:1.3.0")


    // Rootbeer (Root Detection)
    implementation("com.scottyab:rootbeer-lib:0.0.8")

    // Slider
    implementation("com.synnapps:carouselview:0.1.4")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    // Smart Tab Layout
    implementation("com.ogaclejapan.smarttablayout:library:2.0.0@aar")
    implementation("com.ogaclejapan.smarttablayout:utils-v4:2.0.0@aar")

    // Indicator
    implementation("me.relex:circleindicator:2.1.4")

    // Timeline
    implementation("com.github.vipulasri:timelineview:1.0.3")

    // OkHttp Testing
    testImplementation("com.squareup.okhttp3:mockwebserver:3.14.7")

    // InstantExecutorRule
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // AndroidJUnitRunner and JUnit Rules
    androidTestImplementation("androidx.test:runner:1.3.0")
    androidTestImplementation("androidx.test:rules:1.3.0")
    testImplementation("androidx.test.ext:junit-ktx:1.1.2")

    // OTP TextView
    implementation("com.github.mukeshsolanki:android-otpview-pinview:2.1.2")

    // Page Indicator View Pager
    implementation("com.romandanylyk:pageindicatorview:1.0.3")

    // PDF Viewer
    implementation("com.github.barteksc:android-pdf-viewer:2.8.2")

    // Currency Edit Text (Duplikat)
    implementation("me.abhinay.input:currency-edittext:1.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-alpha05")

    // CameraX
    val cameraxVersion = "1.4.1"
    implementation("androidx.camera:camera-core:$cameraxVersion")
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-video:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
    implementation("androidx.camera:camera-extensions:$cameraxVersion")


    implementation("androidx.work:work-runtime:2.9.0")

    // Formula Parsing
    implementation("org.mvel:mvel2:2.4.13.Final")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Architecture Component
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // Image Loader
    implementation("io.coil-kt:coil:2.6.0")

    // Lottie Animation
    implementation("com.airbnb.android:lottie:6.4.0")

    // compose
    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")

    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // LiveData / StateFlow untuk ViewModel
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Navigation Component untuk Compose
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Accompanist (opsional: untuk tambahan fitur)
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.2-alpha")

    // Dependency Injection (Dagger / Hilt)
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0")
    implementation("com.google.dagger:hilt-android:2.44") // Pastikan menggunakan versi terbaru
    kapt("com.google.dagger:hilt-android-compiler:2.44") // Kapt untuk annotation processing

    // Debugging
    debugImplementation("androidx.compose.ui:ui-tooling")

//    security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

//    moshi
    implementation ("com.squareup.moshi:moshi:1.15.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0") 

}