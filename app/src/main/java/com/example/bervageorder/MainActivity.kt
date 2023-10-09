package com.example.bervageorder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bervageorder.navigation.BeverageOrderNavGraph
import com.example.bervageorder.ui.theme.BervageOrderTheme
import dagger.hilt.android.AndroidEntryPoint

// Activity / Fragmnet와는 Compose는 별개이다.
// xml과 Compose와 비교해야지, Compose는 화면을 그리는 방식이 다를 뿐
// Compose는 xml을 대체하는 방식이다.
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
