package com.example.bervageorder.presentation.menudetail.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OrderMenuOption
import com.example.bervageorder.domain.model.OptionTypeSealed
import com.example.bervageorder.domain.model.Temperature
import com.example.bervageorder.presentation.common.button.NextBottomButton
import com.example.bervageorder.presentation.menudetail.CaffeineOptionRow
import com.example.bervageorder.presentation.menudetail.HeaderTitle
import com.example.bervageorder.presentation.menudetail.IceOptionRow
import com.example.bervageorder.presentation.menudetail.IceQuantityOptionRow

@Composable
fun MenuDetailScreen(
    modifier: Modifier = Modifier,
    menu: Menu?,
    orderMenuOption: OrderMenuOption,
    onClickIceOption: (Boolean) -> Unit,
    onClickOption: (OptionTypeSealed) -> Unit,
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
            orderMenuOption = orderMenuOption,
            onClickIceOption = onClickIceOption,
            onClickOption = onClickOption
        )

        NextBottomButton { onClickNext() }
    }
}

@Composable
private fun MenuOptionsColumn(
    modifier: Modifier = Modifier,
    menu: Menu,
    orderMenuOption: OrderMenuOption,
    onClickIceOption: (Boolean) -> Unit,
    onClickOption: (OptionTypeSealed) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        // 선택한 메뉴 명
        HeaderTitle(
            modifier = Modifier.fillMaxWidth(),
            name = menu.name,
            price = menu.priceFormatString
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
        if (menu.isIceQuantity && orderMenuOption.temperature == Temperature.ICE) {
            IceQuantityOptionRow(
                modifier = Modifier.padding(top = 32.dp),
                onClickOption = onClickOption
            )
        }
    }
}