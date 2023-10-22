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
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = { onClick() },
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
fun CloseBottomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BottomButton(
        modifier = modifier,
        text = BottomButtonState.Close.getBottomButtonTextId(),
        onClick = onClick,
    )
}

@Composable
fun NextBottomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    BottomButton(
        modifier = modifier,
        text = BottomButtonState.Next.getBottomButtonTextId(),
        onClick = onClick,
    )
}
