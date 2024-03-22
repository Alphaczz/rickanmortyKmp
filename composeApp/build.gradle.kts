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
                api(libs.androidx.startup)

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
            implementation(libs.ktor.logging)
            implementation(libs.napier)
            implementation(libs.kamel)
            implementation(libs.koin.compose)
            implementation(libs.koin.core)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.koin)
            implementation(libs.sqlDelight.extensions)
            implementation(libs.moko.resources.compose)
            implementation(libs.androidx.datastore.preferences.core)
            val voyagerVersion = "1.0.0-rc07"
            implementation(libs.accompanist.permissions)

            implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")

            // Allows us to use tab navigation for the bottom bar
            implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")

            // Support for transition animations
            implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
            //image picker
            implementation(libs.peekaboo.ui)

            // peekaboo-image-picker
            implementation(libs.peekaboo.image.picker)
            implementation("com.darkrockstudios:mpfilepicker:3.1.0")

            // Moko Permissions
            implementation("dev.icerock.moko:permissions:0.17.0")

            // Moko Permissions Compose
            implementation("dev.icerock.moko:permissions-compose:0.17.0")

            // Moko Media Compose
            implementation("dev.icerock.moko:media-compose:0.11.0")
            implementation("dev.icerock.moko:media:0.11.0")

            // Compose Multiplatform
            implementation("dev.icerock.moko:media-test:0.11.0")
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
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences.core)

}


multiplatformResources {
    multiplatformResourcesPackage = "com.jetbrains.kmpapp"
}
sqldelight {
    databases {
        create("RickandMortyDb") {
            // https://cashapp.github.io/sqldelight
            packageName.set("schema.sqldelight")
            sourceFolders.set(listOf("kotlin"))
        }
    }
}

