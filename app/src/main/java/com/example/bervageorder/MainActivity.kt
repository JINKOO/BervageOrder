package com.example.bervageorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bervageorder.navigation.BeverageOrderNavGraph
import com.example.bervageorder.ui.theme.BervageOrderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BervageOrderTheme {
                BeverageOrderNavGraph()
            }
        }
    }
}
