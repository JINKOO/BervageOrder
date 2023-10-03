package com.example.bervageorder.presentation.menudetail

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bervageorder.R
import com.example.bervageorder.data.entity.TemperatureType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuDetailMainScreen(
    modifier: Modifier = Modifier,
    viewModel: MenuDetailViewModel,
    navigateUp: () -> Unit,
    navigateToOrder: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                titleId = R.string.title_order_screen,
                navigateUp = {
                    navigateUp()
                    viewModel.clearOption()
                }
            )
        },
    ) { paddingValues ->
        OrderContent(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            menu = uiState.menu,
            isShowIceQuantityOption = uiState.isShowIceQuantityOption,
            onClickOption = { id, option -> viewModel.addOption(id, option) },
            onClickNext = {
                viewModel.setSelectedOptions()
                navigateToOrder(viewModel.menuId)
            }
        )
    }
}

@Composable
private fun OrderContent(
    modifier: Modifier = Modifier,
    menu: Menu?,
    isShowIceQuantityOption: Boolean,
    onClickOption: (Int, String) -> Unit,
    onClickNext: () -> Unit
) {
    if (menu != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 선택한 메뉴 명
            HeaderTitle(
                modifier = Modifier.fillMaxWidth(),
                name = menu.name,
                price = menu.price
            )
            if (menu.temperature == TemperatureType.BOTH) {
                DefaultOptionRow(
                    modifier = Modifier.padding(top = 32.dp),
                    onClickOption = onClickOption
                )
            }

            if (menu.isCaffeine) {
                DecaffeineOptionRow(
                    modifier = Modifier.padding(top = 32.dp),
                    onClickOption = onClickOption
                )
            }

            // 얼음 옵션에서 선택이 된 경우, 동적으로 노출되도록 한다.
            if (isShowIceQuantityOption) {
                IceQuantityOptionRow(
                    modifier = Modifier.padding(top = 32.dp),
                    onClickOption = onClickOption
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onClickNext() }
            ) {
                Text(text = "다음")
            }
        }
    }
}

@Composable
private fun HeaderTitle(
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
private fun DefaultOptionRow(
    modifier: Modifier = Modifier,
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
            onClickOption = { onClickOption(0, it) }
        )
    }
}

@Composable
private fun DecaffeineOptionRow(
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
private fun IceQuantityOptionRow(
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
private fun OptionButtonRow(
    modifier: Modifier = Modifier,
    optionList: List<MenuOption> = emptyList(),
    onClickOption: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedOption by remember { mutableStateOf(optionList.first()) }
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
                                onClickOption(context.getString(selectedOption.title))
                            }
                        ),
                    selectedOption = option,
                    textColor = if (isSelected) selectedOption.selectedTextColor else selectedOption.unSelectedTextColor,
                    backgroundColor = if (isSelected) selectedOption.selectedBackGroundColor else selectedOption.unSelectedBackGroundColor
                )
            }
        }
    }
}

@Composable
private fun TwoOptionItem(
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