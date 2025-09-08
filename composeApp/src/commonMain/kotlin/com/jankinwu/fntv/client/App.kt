package com.jankinwu.fntv.client

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jankinwu.fntv.client.data.model.SystemAccountData
import com.jankinwu.fntv.client.data.viewmodel.MediaDbViewModel
import com.jankinwu.fntv.client.data.viewmodel.UiState
import com.jankinwu.fntv.client.icons.Home
import com.jankinwu.fntv.client.ui.screen.HomePageScreen
import com.jankinwu.fntv.client.ui.screen.MediaDbScreen
import io.github.composefluent.ExperimentalFluentApi
import io.github.composefluent.FluentTheme
import io.github.composefluent.animation.FluentDuration
import io.github.composefluent.animation.FluentEasing
import io.github.composefluent.component.AutoSuggestBoxDefaults
import io.github.composefluent.component.AutoSuggestionBox
import io.github.composefluent.component.Icon
import io.github.composefluent.component.ListItem
import io.github.composefluent.component.MenuItem
import io.github.composefluent.component.NavigationDefaults
import io.github.composefluent.component.NavigationDisplayMode
import io.github.composefluent.component.NavigationMenuItemScope
import io.github.composefluent.component.NavigationView
import io.github.composefluent.component.SideNavItem
import io.github.composefluent.component.TextBoxButton
import io.github.composefluent.component.TextBoxButtonDefaults
import io.github.composefluent.component.TextField
import io.github.composefluent.component.rememberNavigationState
import io.github.composefluent.gallery.component.ComponentItem
import io.github.composefluent.gallery.component.ComponentNavigator
import io.github.composefluent.gallery.component.rememberComponentNavigator
import io.github.composefluent.gallery.screen.settings.SettingsScreen
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.regular.ArrowLeft
import io.github.composefluent.icons.regular.Settings
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.ui.tooling.preview.Preview

private val baseComponents = listOf(
    ComponentItem("首页", "首页", "首页", icon = Home, content = { HomePageScreen() })
)

var mediaLibraryComponent: ComponentItem? by mutableStateOf(null)

@OptIn(FlowPreview::class, ExperimentalFluentApi::class)
@Composable
@Preview
fun App(
    navigator: ComponentNavigator = rememberComponentNavigator(components.first()),
    windowInset: WindowInsets = WindowInsets(0),
    contentInset: WindowInsets = WindowInsets(0),
    collapseWindowInset: WindowInsets = WindowInsets(0),
    icon: Painter? = null,
    title: String = "",
) {
    ReadEnvVariable()
    var selectedItemWithContent by remember {
        mutableStateOf(navigator.latestBackEntry)
    }
    LaunchedEffect(navigator.latestBackEntry) {
        val latestBackEntry = navigator.latestBackEntry
        if (selectedItemWithContent == latestBackEntry) return@LaunchedEffect
        if (latestBackEntry == null || latestBackEntry.content != null) {
            selectedItemWithContent = latestBackEntry
        }
    }

    // 创建 MediaDbViewModel 实例
    val mediaDbViewModel: MediaDbViewModel = viewModel()
    val mediaUiState by mediaDbViewModel.uiState.collectAsState()

    // 动态生成组件列表
    LaunchedEffect(mediaUiState, selectedItemWithContent) {
        val state = mediaUiState
        when (state) {
            is UiState.Success -> {
                // 动态生成媒体库组件
                val mediaItems = state.data.map { media ->
                    ComponentItem(
                        name = media.title,
                        group = "媒体库",
                        description = media.title,
                        content = { navigator ->
                            MediaDbScreen(media, navigator)
                        }
                    )
                }

                // 创建媒体库父组件
                mediaLibraryComponent = ComponentItem(
                    name = "媒体库",
                    group = "媒体库",
                    description = "媒体库",
                    icon = Home,
                    content = { selectedItemWithContent?.content },
                    items = mediaItems
                )
            }
            else -> {
                // 请求失败时也创建媒体库组件，但子项为空
                mediaLibraryComponent = ComponentItem(
                    name = "媒体库",
                    group = "媒体库",
                    description = "媒体库",
                    icon = Home,
                    content = {selectedItemWithContent?.content },
                    items = emptyList()
                )
            }
        }
    }

    // 在初始化时加载媒体数据
    LaunchedEffect(Unit) {
        mediaDbViewModel.loadMediaDbList()
    }


    var textFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }

    val settingItem = remember(navigator) {
        ComponentItem(
            "Settings",
            group = "",
            description = "",
            icon = Icons.Default.Settings
        ) { SettingsScreen(navigator) }
    }
    val store = LocalStore.current
    val isCollapsed = store.navigationDisplayMode == NavigationDisplayMode.LeftCollapsed
    NavigationView(
        modifier = Modifier
            .windowInsetsPadding(
                insets = if (isCollapsed) collapseWindowInset else windowInset
            ),
        state = rememberNavigationState(),
        displayMode = store.navigationDisplayMode,
        contentPadding = if (!isCollapsed) {
            PaddingValues()
        } else {
            PaddingValues(top = 48.dp)
        },
        menuItems = {
            components.forEach { navItem ->
                item {
                    MenuItem(
                        navigator.latestBackEntry,
                        navigator::navigate,
                        navItem,
                        navItem.name == "首页"
                    )
                }
            }
        },
        footerItems = {
            item {
                MenuItem(navigator.latestBackEntry, navigator::navigate, settingItem)
            }
        },
        title = {
            if (isCollapsed) {
                if (icon != null) {
                    Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 12.dp).size(16.dp)
                    )
                }
                if (title.isNotEmpty()) {
                    io.github.composefluent.component.Text(
                        text = title,
                        style = FluentTheme.typography.caption,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            } else {
                io.github.composefluent.component.Text("")
            }
        },
        backButton = {
            if (isCollapsed) {
                NavigationDefaults.BackButton(
                    onClick = {
                        navigator.navigateUp()
                    },
                    disabled = !navigator.canNavigateUp,
                    icon = { Icon(Icons.Default.ArrowLeft, contentDescription = null) },
                    modifier = Modifier.windowInsetsPadding(contentInset.only(WindowInsetsSides.Start))
                )
            }
        },
        autoSuggestBox = {
            var expandedSuggestion by remember { mutableStateOf(false) }
            AutoSuggestionBox(
                expanded = expandedSuggestion,
                onExpandedChange = { expandedSuggestion = it }
            ) {
                TextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    placeholder = { io.github.composefluent.component.Text("Search") },
                    trailing = {
                        TextBoxButton(onClick = {}) { TextBoxButtonDefaults.SearchIcon() }
                    },
                    isClearable = true,
                    shape = AutoSuggestBoxDefaults.textFieldShape(expandedSuggestion),
                    modifier = Modifier.fillMaxWidth().focusHandle().flyoutAnchor()
                )
                val searchResult = remember(flatMapComponents) {
                    snapshotFlow {
                        textFieldValue.text
                    }.debounce { if (it.isBlank()) 0 else 200 }
                        .map {
                            flatMapComponents.filter { item ->
                                item.name.contains(
                                    it,
                                    ignoreCase = true
                                ) || item.description.contains(it, ignoreCase = true)
                            }
                        }
                }.collectAsState(flatMapComponents)
                AutoSuggestBoxDefaults.suggestFlyout(
                    expanded = expandedSuggestion,
                    onDismissRequest = { expandedSuggestion = false },
                    modifier = Modifier.flyoutSize(matchAnchorWidth = true),
                    itemsContent = {
                        items(
                            items = searchResult.value,
                            contentType = { "Item" },
                            key = { it.hashCode().toString() }
                        ) {
                            ListItem(
                                onClick = {
                                    navigator.navigate(it)
                                    expandedSuggestion = false
                                },
                                text = {
                                    io.github.composefluent.component.Text(
                                        it.name,
                                        maxLines = 1
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                )
            }
        },
        pane = {
            AnimatedContent(selectedItemWithContent, Modifier.fillMaxSize(), transitionSpec = {
                (fadeIn(
                    tween(
                        FluentDuration.ShortDuration,
                        easing = FluentEasing.FadeInFadeOutEasing,
                        delayMillis = FluentDuration.QuickDuration
                    )
                ) + slideInVertically(
                    tween(
                        FluentDuration.MediumDuration,
                        easing = FluentEasing.FastInvokeEasing,
                        delayMillis = FluentDuration.QuickDuration
                    )
                ) { it / 5 }) togetherWith fadeOut(
                    tween(
                        FluentDuration.QuickDuration,
                        easing = FluentEasing.FadeInFadeOutEasing,
                        delayMillis = FluentDuration.QuickDuration
                    )
                )
            }) {
                if (it != null) {
                    it.content?.invoke(it, navigator)
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        io.github.composefluent.component.Text(
                            "No content selected",
                            style = FluentTheme.typography.bodyStrong
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun NavigationMenuItemScope.MenuItem(
    selectedItem: ComponentItem?,
    onSelectedItemChanged: (ComponentItem) -> Unit,
    navItem: ComponentItem,
    hasSeparator: Boolean = false
) {
    val expandedItems = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(selectedItem) {
        if (selectedItem == null) return@LaunchedEffect
        if (navItem != selectedItem) {
            val navItemAsGroup = "${navItem.group}/${navItem.name}/"
            if ((selectedItem.group + "/").startsWith(navItemAsGroup))
                expandedItems.value = true
        }
    }
    val flyoutVisible = remember {
        mutableStateOf(false)
    }
    if (!hasSeparator) {
        MenuItem(
            selected = selectedItem == navItem,
            onClick = {
                onSelectedItemChanged(navItem)
                expandedItems.value = !expandedItems.value
                if (navItem.items.isNullOrEmpty()) {
                    flyoutDismissRequest()
                }
            },
            icon = navItem.icon?.let { { Icon(it, navItem.name) } },
            text = { io.github.composefluent.component.Text(navItem.name) },
            expandItems = expandedItems.value || flyoutVisible.value,
            onExpandItemsChanged = { flyoutVisible.value = it },
            items = navItem.items?.let {
                if (it.isNotEmpty()) {
                    {
                        it.forEach { nestedItem ->
                            NavigationItem(
                                selectedItem = selectedItem,
                                onSelectedItemChanged = {
                                    onSelectedItemChanged(nestedItem)
                                },
                                navItem = nestedItem,
                                onFlyoutDismissRequest = {
                                    isFlyoutVisible = false
                                    flyoutDismissRequest()
                                }
                            )
                        }
                    }
                } else {
                    null
                }
            }
        )
    } else {
        MenuItem(
            selected = selectedItem == navItem,
            onClick = {
                onSelectedItemChanged(navItem)
                expandedItems.value = !expandedItems.value
            },
            icon = navItem.icon?.let { { Icon(it, navItem.name) } },
            text = { io.github.composefluent.component.Text(navItem.name) },
            expandItems = expandedItems.value || flyoutVisible.value,
            onExpandItemsChanged = { flyoutVisible.value = it },
            items = navItem.items?.let {
                if (it.isNotEmpty()) {
                    {
                        it.forEach { nestedItem ->
                            NavigationItem(
                                selectedItem = selectedItem,
                                onSelectedItemChanged = {
                                    onSelectedItemChanged(nestedItem)
                                },
                                navItem = nestedItem,
                                onFlyoutDismissRequest = {
                                    isFlyoutVisible = false
                                    flyoutDismissRequest()
                                }
                            )
                        }
                    }
                } else {
                    null
                }
            },
            header = null,
            separatorVisible = true
        )
    }
}

@Composable
private fun NavigationItem(
    selectedItem: ComponentItem?,
    onSelectedItemChanged: (ComponentItem) -> Unit,
    navItem: ComponentItem,
    onFlyoutDismissRequest: () -> Unit = {},
) {
    val expandedItems = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(selectedItem) {
        if (selectedItem == null) return@LaunchedEffect
        if (navItem != selectedItem) {
            val navItemAsGroup = "${navItem.group}/${navItem.name}/"
            if ((selectedItem.group + "/").startsWith(navItemAsGroup))
                expandedItems.value = true
        }
    }
    SideNavItem(
        selectedItem == navItem,
        onClick = {
            onSelectedItemChanged(navItem)
            if (navItem.items == null) {
                onFlyoutDismissRequest()
            } else {
                expandedItems.value = !expandedItems.value
            }
        },
        icon = navItem.icon?.let { { Icon(it, navItem.name) } },
        content = { io.github.composefluent.component.Text(navItem.name) },
        expandItems = expandedItems.value,
        items = navItem.items?.let {
            if (it.isNotEmpty()) {
                {
                    it.forEach { nestedItem ->
                        NavigationItem(
                            selectedItem = selectedItem,
                            onSelectedItemChanged = onSelectedItemChanged,
                            navItem = nestedItem,
                            onFlyoutDismissRequest = onFlyoutDismissRequest
                        )
                    }
                }
            } else {
                null
            }
        }
    )
}

val components: List<ComponentItem>
    get() = buildList {
        // 始终添加首页
        addAll(baseComponents)
        // 动态添加媒体库（如果已生成）
        mediaLibraryComponent?.let { add(it) }
    }
val flatMapComponents: List<ComponentItem> by lazy {
    listOf(
        ComponentItem("测试", "测试组", "测试描述", content = null)
    )
}

@Composable
internal fun ReadEnvVariable() {
    val fnOfficialBaseUrl = System.getenv("FN_OFFICIAL_BASE_URL")
    if (fnOfficialBaseUrl != null) {
        println("FN_OFFICIAL_BASE_URL: $fnOfficialBaseUrl")
        SystemAccountData.fnOfficialBaseUrl = fnOfficialBaseUrl
    } else {
        println("FN_OFFICIAL_BASE_URL: null")
    }
    val fnTvBackendBaseUrl = System.getenv("FN_TV_BACKEND_BASE_URL")
    if (fnTvBackendBaseUrl != null) {
        println("FN_TV_BACKEND_BASE_URL: $fnTvBackendBaseUrl")
        SystemAccountData.fnTvBackendBaseUrl = fnTvBackendBaseUrl
    } else {
        println("FN_TV_BACKEND_BASE_URL: null")
    }
    val authorization = System.getenv("AUTHORIZATION")
    if (authorization != null) {
        println("AUTHORIZATION: $authorization")
        SystemAccountData.authorization = authorization
    }
}