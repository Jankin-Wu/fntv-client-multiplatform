import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

var LAST_VERSION = "3.3.2"

kotlin {
    jvm()
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.webview)
            implementation(libs.ktor.http)
//            implementation("com.github.ltttttttttttt:load-the-image:1.0.9")
            // 提供了 Sketch 的核心功能以及单例和依赖单例实现的扩展函数
//            implementation("io.github.panpf.sketch4:sketch-compose:${LAST_VERSION}")
//// 提供了加载网络图片的能力
//            implementation("io.github.panpf.sketch4:sketch-http:${LAST_VERSION}")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.androidx.runtime.desktop)
            implementation(libs.vlcj)
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.jankinwu.fntv.MainKt"
        buildTypes.release.proguard {
            configurationFiles.from("compose-desktop.pro")
        }
        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED")

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.jankinwu.fntv"
            packageVersion = "1.0.0"
        }
    }
}
