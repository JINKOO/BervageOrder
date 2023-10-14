package com.example.bervageorder.presentation.menudetail.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bervageorder.data.entity.TemperatureType
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.presentation.menudetail.DecaffeineOptionRow
import com.example.bervageorder.presentation.menudetail.IceOptionRow
import com.example.bervageorder.presentation.menudetail.HeaderTitle
import com.example.bervageorder.presentation.menudetail.IceQuantityOptionRow

@Composable
fun MenuDetailScreen(
    modifier: Modifier = Modifier,
    menu: Menu?,
    isShowIceQuantityOption: Boolean,
    onClickIceOption: () -> Unit,
    onClickOption: (Int, String) -> Unit,
    onClickNext: () -> Unit
) {
    if (menu == null) return
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MenuOptionsColumn(
            menu = menu,
            isShowIceQuantityOption = isShowIceQuantityOption,
            onClickIceOption = onClickIceOption,
            onClickOption = onClickOption
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onClickNext() }
        ) {
            Text(text = "다음")
        }
    }
}

@Composable
private fun MenuOptionsColumn(
    modifier: Modifier = Modifier,
    menu: Menu,
    onClickIceOption: () -> Unit,
    isShowIceQuantityOption: Boolean,
    onClickOption: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 선택한 메뉴 명
        HeaderTitle(
            modifier = Modifier.fillMaxWidth(),
            name = menu.name,
            price = menu.price
        )
        if (menu.temperature == TemperatureType.BOTH) {
            IceOptionRow(
                modifier = Modifier.padding(top = 32.dp),
                onClickIceOption = onClickIceOption,
                onClickOption = onClickOption
            )
        }

        if (menu.isCaffeine) {
            DecaffeineOptionRow(
                modifier = Modifier.padding(top = 32.dp),
                onClickOption = onClickOption
            )
        }

        // 동적으로 노출되도록 변경
        if (menu.isIceQuantityNeed && isShowIceQuantityOption) {
            IceQuantityOptionRow(
                modifier = Modifier.padding(top = 32.dp),
                onClickOption = onClickOption
            )
        }
    }
}