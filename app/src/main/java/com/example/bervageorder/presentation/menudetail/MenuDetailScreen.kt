package com.example.bervageorder.presentation.menudetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.common.button.BottomButton
import com.example.bervageorder.presentation.common.button.BottomButtonState
import com.example.bervageorder.presentation.menudetail.state.OptionType

@Composable
fun MenuDetailScreen(
    modifier: Modifier = Modifier,
    menu: Menu?,
    isShowIceQuantityOption: Boolean,
    onClickIceOption: (Boolean) -> Unit,
    onClickOption: (Int, OptionType) -> Unit,
    onClickNext: () -> Unit
) {
    if (menu == null) return
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MenuOptionsColumn(
            menu = menu,
            isShowIceQuantityOption = isShowIceQuantityOption,
            onClickIceOption = onClickIceOption,
            onClickOption = onClickOption
        )

        BottomButton(
            bottomButtonState = BottomButtonState.Next,
            onClick = onClickNext
        )
    }
}

@Composable
private fun MenuOptionsColumn(
    modifier: Modifier = Modifier,
    menu: Menu,
    onClickIceOption: (Boolean) -> Unit,
    isShowIceQuantityOption: Boolean,
    onClickOption: (Int, OptionType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // 선택한 메뉴 명
        HeaderTitle(
            modifier = Modifier.fillMaxWidth(),
            name = menu.name,
            price = menu.price
        )
        if (menu.isDefaultOption) {
            IceOptionRow(
                modifier = Modifier.padding(top = 32.dp),
                onClickIceOption = onClickIceOption,
                onClickOption = onClickOption
            )
        }

        if (menu.isCaffeine) {
            CaffeineOptionRow(
                modifier = Modifier.padding(top = 32.dp),
                onClickOption = onClickOption
            )
        }

        // 동적으로 노출되도록 변경
        if (menu.isIceQuantity && isShowIceQuantityOption) {
            IceQuantityOptionRow(
                modifier = Modifier.padding(top = 32.dp),
                onClickOption = onClickOption
            )
        }
    }
}