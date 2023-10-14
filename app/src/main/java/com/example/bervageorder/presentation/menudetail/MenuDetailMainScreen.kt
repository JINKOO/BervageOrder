package com.example.bervageorder.presentation.menudetail

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bervageorder.R
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBar
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBarState
import com.example.bervageorder.presentation.menudetail.state.MenuDetailScreen
import com.example.bervageorder.presentation.menudetail.state.MenuDetailUiState
import com.example.bervageorder.presentation.menudetail.state.MenuOption
import com.example.bervageorder.presentation.menudetail.state.deCaffeineOptionList
import com.example.bervageorder.presentation.menudetail.state.defaultOptionLists
import com.example.bervageorder.presentation.menudetail.state.iceQuantityOptionList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDetailMainScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuDetailViewModel,
    navigateUp: () -> Unit,
    navigateToOrder: (String) -> Unit
) {
    val uiState: MenuDetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                state = BeverageOrderTopAppBarState.MenuDetailTitle,
                navigateUp = {
                    navigateUp()
                    viewModel.clearOption()
                }
            )
        },
    ) { paddingValues ->
        var isShowIceQuantityOption by rememberSaveable { mutableStateOf(false) }
        when(uiState) {
            is MenuDetailUiState.None -> {}
            is MenuDetailUiState.Loading -> {}
            is MenuDetailUiState.Success -> {
                MenuDetailScreen(
                    modifier = modifier.padding(paddingValues = paddingValues),
                    menu = (uiState as MenuDetailUiState.Success).menu,
                    onClickOption = { id, option -> viewModel.addOption(id, option) },
                    onClickIceOption = { isShowIceQuantityOption = true },
                    isShowIceQuantityOption = isShowIceQuantityOption,
                    onClickNext = { viewModel.setSelectedOptions() }
                )
            }
            is MenuDetailUiState.Error -> {}
        }
    }

    // TODO 에러 메세지 보여주는 로직 수정, SnackBar로 보여주기
//    if (uiState.isShowMessage) {
//        Toast.makeText(context, "메뉴 옵션을 선택해 주세요~", Toast.LENGTH_SHORT).show()
//        viewModel.showToastDone()
//    }
//
//    LaunchedEffect(key1 = uiState.isNavigateToNext) {
//        if(uiState.isNavigateToNext) {
//            navigateToOrder(viewModel.menuId)
//        }
//    }
}



@Composable
fun HeaderTitle(
    modifier: Modifier = Modifier,
    name: String,
    price: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = price,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun IceOptionRow(
    modifier: Modifier = Modifier,
    onClickIceOption: () -> Unit,
    onClickOption: (Int, String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.title_default_option),
            style = MaterialTheme.typography.headlineMedium
        )
        OptionButtonRow(
            optionList = defaultOptionLists,
            onClickOption = { onClickIceOption(); onClickOption(0, it) }
        )
    }
}

@Composable
fun DecaffeineOptionRow(
    modifier: Modifier = Modifier,
    onClickOption: (Int, String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.title_decaffeine_option),
            style = MaterialTheme.typography.headlineMedium
        )
        OptionButtonRow(
            optionList = deCaffeineOptionList,
            onClickOption = { onClickOption(1, it) }
        )
    }
}

@Composable
fun IceQuantityOptionRow(
    modifier: Modifier = Modifier,
    onClickOption: (Int, String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.title_ice_option),
            style = MaterialTheme.typography.headlineMedium
        )
        OptionButtonRow(
            optionList = iceQuantityOptionList,
            onClickOption = { onClickOption(2, it) }
        )
    }
}

@Composable
fun OptionButtonRow(
    modifier: Modifier = Modifier,
    optionList: List<MenuOption> = emptyList(),
    onClickOption: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedOption by remember { mutableStateOf<MenuOption?>(null) }
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            optionList.forEach { option ->
                val isSelected = selectedOption == option
                TwoOptionItem(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = isSelected,
                            onClick = {
                                selectedOption = option
                                selectedOption?.let { onClickOption(context.getString(it.title)) }
                            }
                        ),
                    selectedOption = option,
                    textColor =  selectedOption?.let { if (isSelected) it.selectedTextColor else it.unSelectedTextColor } ?: Color.Black,
                    backgroundColor = selectedOption?.let { if (isSelected) it.selectedBackGroundColor else it.unSelectedBackGroundColor } ?: Color.LightGray
                )
            }
        }
    }
}

@Composable
fun TwoOptionItem(
    modifier: Modifier = Modifier,
    selectedOption: MenuOption,
    @ColorRes textColor: Color,
    @ColorRes backgroundColor: Color
) {
    Box(
        modifier = modifier.background(backgroundColor)
    ) {
        Text(
            text = stringResource(id = selectedOption.title),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
    }
}