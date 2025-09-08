package io.github.composefluent.gallery.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

class ComponentItem(
    val name: String = "",
    val group: String,
    val description: String,
    val items: List<ComponentItem>? = null,
    val icon: ImageVector? = null,
    val guid: String? = null,
    val type: List<String>? = listOf(),
    val content: (@Composable ComponentItem.(navigator: ComponentNavigator) -> Unit)?,
)

