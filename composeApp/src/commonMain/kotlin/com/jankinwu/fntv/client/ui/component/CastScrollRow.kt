package com.jankinwu.fntv.client.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.data.convertor.convertPersonToScrollRowItemData
import com.jankinwu.fntv.client.data.model.ScrollRowItemData
import com.jankinwu.fntv.client.data.model.response.PersonList
import com.jankinwu.fntv.client.data.model.response.PersonListResponse
import com.jankinwu.fntv.client.viewmodel.PersonListViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import io.github.composefluent.FluentTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CastScrollRow(
    modifier: Modifier = Modifier,
    guid: String,
) {
    val personListViewModel: PersonListViewModel = koinViewModel()
    val personListState by personListViewModel.uiState.collectAsState()
    var personList: List<PersonList> by remember { mutableStateOf(emptyList()) }
    var scrollRowItemList by remember { mutableStateOf(emptyList<ScrollRowItemData>()) }
    LaunchedEffect(Unit) {
        personListViewModel.loadData(guid)
    }
    LaunchedEffect(personListState) {
        when (personListState) {
            is UiState.Success -> {
                personList = (personListState as UiState.Success<PersonListResponse>).data.list
                scrollRowItemList = convertPersonToScrollRowItemData(personList)
                print("scrollRowItemList: $scrollRowItemList")
            }

            is UiState.Error -> {
                println("message: ${(personListState as UiState.Error).message}")
            }

            else -> {}
        }
    }
    if (scrollRowItemList.isNotEmpty()) {
        Column(
            modifier = modifier
                .height(140.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 媒体库标题行
            Row(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 12.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            // 在组件内部实现导航逻辑
//                        val targetComponent = components
//                            .firstOrNull { it.name == "媒体库" }
//                            ?.items
//                            ?.firstOrNull { it.guid == guid }

//                        targetComponent?.let {
//                            navigator.navigate(it)
//                        }
                        }
                    )
                    .pointerHoverIcon(PointerIcon.Hand),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "演职人员",
                    style = LocalTypography.current.title.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = FluentTheme.colors.text.text.primary
                )
            }

            ScrollRow(scrollRowItemList) { _, movie, modifier, _ ->
                CastAvatar(
                    modifier = modifier,
                    imageUrl = movie.posterImg,
                    castName = movie.title,
                    role = movie.subtitle
                )
            }
        }
    }
}