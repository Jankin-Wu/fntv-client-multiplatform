package com.jankinwu.fntv.client.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToastManager {
    private val _toasts = mutableStateListOf<ToastMessage>()
    val toasts: SnapshotStateList<ToastMessage> = _toasts

    fun showToast(message: String, icon: ImageVector? = null, duration: Long = 2000L) {
        val toast = ToastMessage(
            message = message,
            icon = icon,
            duration = duration
        )
        _toasts.add(toast)
    }

    fun removeToast(id: String) {
        _toasts.removeAll { it.id == id }
    }
}

@Composable
fun rememberToastManager(): ToastManager {
    return remember { ToastManager() }
}

@Composable
fun ToastHost(
    toastManager: ToastManager,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 72.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top
        ) {
            toastManager.toasts.forEach { toast ->
                Toast(
                    message = toast.message,
                    icon = toast.icon,
                    duration = toast.duration
                ) {
                    toastManager.removeToast(toast.id)
                }
            }
        }
    }
}