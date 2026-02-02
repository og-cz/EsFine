package com.example.espin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.espin.ui.screens.MainScreen
import com.example.espin.ui.theme.EspinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EspinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = com.example.espin.ui.theme.BackgroundOffWhite
                ) {
                    val viewModel: com.example.espin.ui.viewmodel.MainViewModel = viewModel()
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}