package com.example.bervageorder.presentation.order.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bervageorder.domain.model.Menu
import com.example.bervageorder.domain.model.OrderMenuOption
import com.example.bervageorder.presentation.common.button.BottomButton
import com.example.bervageorder.presentation.common.button.BottomButtonState
import com.example.bervageorder.presentation.common.button.CloseBottomButton

@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    orderMenuOption: OrderMenuOption,
    navigateToIntro: () -> Unit
) {
    // TODO 2회차 질문 :: 23번째 줄에서 Menu를 Nullable로 받는데, Composable내에서 29번째 처럼 처리해도 되는지?
    //  아니면 애초에 Domain에서 Model을 넘길때, NonNull타입으로 넘겨야하는지?
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = orderMenuOption.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = orderMenuOption.getOptionFormatString(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Text(
                text = orderMenuOption.getPriceFormatString(),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        CloseBottomButton {
            navigateToIntro()
        }
    }
}