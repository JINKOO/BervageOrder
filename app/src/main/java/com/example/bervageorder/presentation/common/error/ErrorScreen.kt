package com.example.bervageorder.presentation.common.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

interface ErrorState {
    val errorMessageId: Int
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorState: ErrorState
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Filled.Warning,
            contentDescription = null
        )
        Text(
            text = stringResource(errorState.errorMessageId),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// TODO 추상 클래스 interface 차이
interface Word {
    val word: String
}

class WordText {
    private var text1: String = ""
    private var text2: String = ""

    var text3: String = ""
        get() = ""
        set(value) {
            field = "$value"
        }
}