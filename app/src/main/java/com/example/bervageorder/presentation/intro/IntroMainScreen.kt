package com.example.bervageorder.presentation.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bervageorder.R
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBar
import com.example.bervageorder.presentation.common.BeverageOrderTopAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroMainScreen(
    modifier: Modifier = Modifier,
    navigateToMenuList: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BeverageOrderTopAppBar(
                state = BeverageOrderTopAppBarState.IntroTitle,
                navigateUp = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(top = 64.dp, start = 16.dp),
                text = stringResource(R.string.title_greeting_start_order),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineLarge
            )

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
}