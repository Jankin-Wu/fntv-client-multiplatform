package io.github.composefluent.gallery.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.jankinwu.fntv.client.components

interface ComponentNavigator {

    fun navigate(componentItem: ComponentItem)

    fun navigateUp()

    val currentBackstack: List<ComponentItem>

    val latestBackEntry: ComponentItem?

    val canNavigateUp: Boolean

    fun addStartItem(componentItem: ComponentItem);

}

@Composable
fun rememberComponentNavigator(): ComponentNavigator {
    return remember { ComponentNavigatorImpl() }
}

private class ComponentNavigatorImpl() : ComponentNavigator {

    companion object {
        private val NOT_ADD_ITEM_NAME_LIST = listOf("媒体库")
    }

    private val backstack = mutableStateListOf<ComponentItem>()

    override fun navigate(componentItem: ComponentItem) {
        if (!NOT_ADD_ITEM_NAME_LIST.contains(componentItem.name)) {
            backstack.add(componentItem)
        }
        if (backstack.size > 20) {
            backstack.removeAt(0)
        }
    }

    override fun navigateUp() {
        if (backstack.isNotEmpty()) {
            do {
                backstack.removeAt(backstack.lastIndex)
            } while (backstack.lastOrNull().let { it != null && it.content == null })
        }
    }

    override val canNavigateUp: Boolean by derivedStateOf {
        backstack.count { it.content != null } > 1
    }

    override fun addStartItem(componentItem: ComponentItem) {
        backstack.add(componentItem)
    }

    override val currentBackstack: List<ComponentItem>
        get() = backstack

    override val latestBackEntry: ComponentItem?
        get() = backstack.lastOrNull()
}