package com.example.bervageorder.presentation.common.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Text(
            text = text
        )
    }
}

// TODO Wrapper 클래스로 한번 더 감싼다.
//  모든 화면에서 해당 버튼을 많이 사용할 경우, 한번 더 감싼다.
@Composable
fun NextBottomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BottomButton(
        text = BottomButtonState.Next.getBottomButtonText(),
        onClick = onClick
    )
}

@Composable
fun CloseBottomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    BottomButton(
        text = BottomButtonState.Close.getBottomButtonText(),
        onClick = onClick
    )
}