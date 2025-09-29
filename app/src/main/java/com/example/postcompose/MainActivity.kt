package com.example.postcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel // Import for viewModel()
import com.example.postcompose.screens.Screen.Posts.AppNavigation
import com.example.postcompose.ui.theme.PostComposeTheme
import com.example.postcompose.viewmodel.PostsViewModel // Assuming your ViewModel is here

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostComposeTheme {
                Surface(modifier = Modifier.fillMaxSize().safeContentPadding()){
                    AppNavigation()
                }

            }
        }
    }
}

