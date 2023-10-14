package com.example.bervageorder.presentation.menudetail

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bervageorder.R
import com.example.bervageorder.presentation.common.topbar.BeverageOrderTopAppBar
import com.example.bervageorder.presentation.common.topbar.BeverageOrderTopAppBarState
import com.example.bervageorder.presentation.menudetail.state.MenuDetailUiState
import com.example.bervageorder.presentation.menudetail.state.MenuOptionState
import com.example.bervageorder.presentation.menudetail.state.OptionType
import com.example.bervageorder.presentation.menudetail.state.getOptionStringResId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDetailMainScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuDetailViewModel = hiltViewModel(),
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
        // TODO 2회차 질문 :: 얼음 선택 동적 노출 여부에 대한 상태값은
        //  ViewModel에서 처리하는게 맞는 건지, Composable에서 remeber변수를 사용한 상태값으로 처리해도 되는지,
        var isShowIceQuantityOption by rememberSaveable { mutableStateOf(false) }
        when(uiState) {
            is MenuDetailUiState.None -> {}
            is MenuDetailUiState.Loading -> {}
            is MenuDetailUiState.Success -> {
                MenuDetailScreen(
                    modifier = modifier.padding(paddingValues = paddingValues),
                    menu = (uiState as MenuDetailUiState.Success).menu,
                    onClickOption = { id, option -> viewModel.addOption(id, option) },
                    onClickIceOption = { isShowIceQuantityOption = it },
                    isShowIceQuantityOption = isShowIceQuantityOption,
                    onClickNext = { viewModel.setSelectedOptions() }
                )
            }
            is MenuDetailUiState.AllOptionSelected -> { navigateToOrder(viewModel.menuId) }
            is MenuDetailUiState.Error -> {}
        }
    }
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
    onClickIceOption: (Boolean) -> Unit,
    onClickOption: (Int, OptionType) -> Unit
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
            optionList = MenuOptionState.getDefaultOptionList(),
            onClickOption = {
                if (it == OptionType.ICE) {
                    onClickIceOption(true)
                } else {
                    onClickIceOption(false)
                }
                onClickOption(0, it)
            }
        )
    }
}

@Composable
fun CaffeineOptionRow(
    modifier: Modifier = Modifier,
    onClickOption: (Int, OptionType) -> Unit
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
            optionList = MenuOptionState.getCaffeineOptionList(),
            onClickOption = { onClickOption(1, it) }
        )
    }
}

@Composable
fun IceQuantityOptionRow(
    modifier: Modifier = Modifier,
    onClickOption: (Int, OptionType) -> Unit
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
            optionList = MenuOptionState.getIceQuantityOptionList(),
            onClickOption = { onClickOption(2, it) }
        )
    }
}

@Composable
fun OptionButtonRow(
    modifier: Modifier = Modifier,
    optionList: List<MenuOptionState> = emptyList(),
    onClickOption: (OptionType) -> Unit
) {
    var selectedOption by remember { mutableStateOf<MenuOptionState?>(null) }
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
                                onClickOption(option.type)
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
    selectedOption: MenuOptionState,
    @ColorRes textColor: Color,
    @ColorRes backgroundColor: Color
) {
    Box(
        modifier = modifier
            .height(32.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = selectedOption.getOptionStringResId()),
            style = MaterialTheme.typography.bodyLarge,
            color = textColor
        )
    }
}