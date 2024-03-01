plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.mokoResources)
    alias(libs.plugins.sqlDelight)

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        // Required for moko-resources to work
        applyDefaultHierarchyTemplate()

        androidMain {
            dependencies {
                implementation(project.dependencies.platform(libs.compose.bom))
                implementation(libs.compose.ui)
                implementation(libs.koin.android)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.sqlDelight.driver.android)

            }

            // Required for moko-resources to work
            dependsOn(commonMain.get())
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.sqlDelight.driver.native)
            }

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.napier)
            implementation(libs.kamel)
            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.moko.resources.compose)
            implementation(libs.sqlDelight.extensions)
            implementation(libs.androidx.datastore.preferences.core)
        }

    }
}

android {
    namespace = "com.jetbrains.kmpapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.jetbrains.kmpapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}
dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)

}


multiplatformResources {
    multiplatformResourcesPackage = "com.jetbrains.kmpapp"
}
sqldelight {
    databases {
        create("RickandMortyDb") {
            // https://cashapp.github.io/sqldelight
            packageName.set("com.jetbrains.kmpapp.data.repository.localRepo.sqldelight")
            sourceFolders.set(listOf("kotlin"))
        }
    }
}

