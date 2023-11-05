package com.example.bervageorder.presentation.intro.state

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bervageorder.R
import com.example.bervageorder.presentation.common.button.BottomButton
import com.example.bervageorder.presentation.common.button.BottomButtonState
import com.example.bervageorder.presentation.common.button.NextBottomButton

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    @StringRes introTitleId: Int,
    navigateToMenuList: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(top = 48.dp),
            text = stringResource(introTitleId),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineLarge
        )

        NextBottomButton(
            onClick = navigateToMenuList
        )
    }
}