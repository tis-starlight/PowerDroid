plugins {
    id("com.android.application")
}

android {
    namespace = "com.starlight.powerdroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.starlight.powerdroid"
        minSdk = 28
        targetSdk = 34
        versionCode = 1         // integer, not shown to users, increase value: 1,2,3,4,...  , greatest value: 2100000000
        versionName = "1.0"     // string, shown to users, increase value: "1.0", "1.1", ... , "1.9","2.0" or "1.0-demo" ,...

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}