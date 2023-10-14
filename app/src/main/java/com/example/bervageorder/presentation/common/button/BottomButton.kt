package com.example.bervageorder.presentation.common.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    bottomButtonState: BottomButtonState,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Text(
            text = stringResource(id = bottomButtonState.getBottomButtonTextId())
        )
    }
}