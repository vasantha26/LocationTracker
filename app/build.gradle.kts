import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("realm-android")
    id("com.google.gms.google-services")
//apply plugin: 'com.google.gms.google-services'
}


android {
    namespace = "com.demoapp.locationtrack"
    compileSdk = 34

    // Load properties from local.defaults.properties file
    val localPropertiesFile = file("local.defaults.properties")
    val localProperties = Properties().apply {
        load(localPropertiesFile.inputStream())
    }

    // Access the MAPS_API_KEY property
    val mapsApiKey: String? = localProperties.getProperty("MAPS_API_KEY")

    defaultConfig {
        applicationId = "com.demoapp.locationtrack"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Assign the API key to your application's manifest placeholders
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey.toString()

        // Add your API key as a string resource
        resValue("string", "google_maps_api_key", mapsApiKey.toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        dataBinding=true
    }


}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.android.libraries.places:places:3.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //lifecycle
    fun lifecycle_version() = "2.7.0"
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycle_version()}")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${lifecycle_version()}")
    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:${lifecycle_version()}")

    //coroutines
//Coroutines and LifeCycle Libraries
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    //navigation
    fun nav_version() = "2.7.6"

    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:${nav_version()}")
    implementation("androidx.navigation:navigation-ui-ktx:${nav_version()}")


 //dagger
    implementation("com.google.dagger:dagger:2.43.2")
    implementation("com.google.dagger:dagger-android:2.38.1")
    implementation("com.google.dagger:dagger-android-support:2.38.1")
    kapt("com.google.dagger:dagger-compiler:2.43.2")
    kapt("com.google.dagger:dagger-android-processor:2.38.1")

    implementation ("androidx.credentials:credentials:1.2.2")
    implementation ("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation ("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    implementation ("com.google.android.gms:play-services-auth:20.0.0")




}



