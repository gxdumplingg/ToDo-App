plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.todoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    dependencies {
        val room_version = "2.6.1"
        val nav_version = "2.7.2"

        implementation ("androidx.viewpager2:viewpager2:1.0.0")

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation("androidx.recyclerview:recyclerview:1.3.0")

        implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
        implementation("com.google.android.material:material:1.12.0")

        // Room Dependencies
        implementation("androidx.room:room-runtime:$room_version")
        kapt("androidx.room:room-compiler:$room_version")
        implementation("androidx.room:room-ktx:$room_version")
        implementation("androidx.room:room-rxjava2:$room_version")
        implementation("androidx.room:room-rxjava3:$room_version")
        implementation("androidx.room:room-guava:$room_version")
        testImplementation("androidx.room:room-testing:$room_version")
        implementation("androidx.room:room-paging:$room_version")

        // Lifecycle
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

        // Navigation
        implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
        implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

        // Testing
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
}