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

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    @StringRes introTitleId: Int,
    navigateToMenuList: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(top = 64.dp, start = 16.dp),
            text = stringResource(introTitleId),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.headlineLarge
        )

        // TODO 이 앱에서 공통으로 사용하는 BottomButton으로 사용할 수 있도록 추상화
        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .fillMaxWidth(),
            onClick = {
                navigateToMenuList()
            }
        ) {
            Text(
                text = stringResource(R.string.button_next),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}