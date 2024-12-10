plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace =
        "pl.dawid.projektiimplementacjaaplikacjimobilnejdoplanowaniaizarzdzaniabudetemwarsztatusamochodowego"
    compileSdk = 34

    defaultConfig {
        applicationId =
            "pl.dawid.projektiimplementacjaaplikacjimobilnejdoplanowaniaizarzdzaniabudetemwarsztatusamochodowego"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                var arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
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
}

dependencies {
    implementation(libs.firebase.vertexai)
    val room_version = "2.5.2"
    val lifecycle_version = "2.6.2"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)
}