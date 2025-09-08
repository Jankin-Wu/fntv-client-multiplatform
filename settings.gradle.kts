rootProject.name = "fntv-client-multiplatfrm"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenLocal()
        maven("https://jitpack.io")
//        maven("https://maven.aliyun.com/repository/public")
//        maven("https://maven.aliyun.com/repository/google")
//        maven("https://maven.aliyun.com/repository/jcenter")
//        maven("https://maven.aliyun.com/repository/gradle-plugin")
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        maven("https://jitpack.io")
//        maven("https://maven.aliyun.com/repository/public")
//        maven("https://maven.aliyun.com/repository/google")
//        maven("https://maven.aliyun.com/repository/jcenter")
//        maven("https://maven.aliyun.com/repository/gradle-plugin")
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        maven("https://artifacts.alfresco.com/nexus/content/repositories/public/")
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jogamp.org/deployment/maven/")
        maven("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")