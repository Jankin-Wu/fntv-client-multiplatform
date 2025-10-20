package com.jankinwu.fntv.client.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jankinwu.fntv.client.LocalTypography
import com.jankinwu.fntv.client.components
import com.jankinwu.fntv.client.data.convertor.convertPersonToScrollRowItemData
import com.jankinwu.fntv.client.data.model.ScrollRowItemData
import com.jankinwu.fntv.client.data.model.response.PersonListResponse
import com.jankinwu.fntv.client.viewmodel.PersonListViewModel
import com.jankinwu.fntv.client.viewmodel.UiState
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.Icon
import io.github.composefluent.icons.Icons
import io.github.composefluent.icons.filled.IosArrowRtl
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CastScrollRow(
    modifier: Modifier = Modifier,
    guid: String,
) {
    val personListViewModel: PersonListViewModel = koinViewModel()
    val personListState by personListViewModel.personListState.collectAsState()
    var personList by remember { mutableStateOf(emptyList<PersonListResponse>()) }
    var scrollRowItemList by remember { mutableStateOf(emptyList<ScrollRowItemData>()) }
    LaunchedEffect(Unit) {
        personListViewModel.loadPersonList(guid)
    }
    LaunchedEffect(personListState) {
        when (personListState) {
            is UiState.Success -> {
                personList = (personListState as UiState.Success).data
                scrollRowItemList = convertPersonToScrollRowItemData(personList)
            }

            else -> {}
        }
    }
    Column(
        modifier = modifier
            .height(50.dp),
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
                        val targetComponent = components
                            .firstOrNull { it.name == "媒体库" }
                            ?.items
                            ?.firstOrNull { it.guid == guid }

//                        targetComponent?.let {
//                            navigator.navigate(it)
//                        }
                    }
                )
                .pointerHoverIcon(PointerIcon.Hand),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "",
                style = LocalTypography.current.title.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = FluentTheme.colors.text.text.tertiary
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Filled.IosArrowRtl,
                contentDescription = "View More",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.requiredSize(11.dp)
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