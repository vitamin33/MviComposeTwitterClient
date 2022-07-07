package com.serbyn.mvicomposetwitterclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.accompanist.insets.ProvideWindowInsets
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.serbyn.mvicomposetwitterclient.databinding.ContentMainBinding
import com.serbyn.mvicomposetwitterclient.ui.theme.MviComposeTwitterClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviComposeTwitterClientTheme {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    AndroidViewBinding(ContentMainBinding::inflate)
                }
            }
        }
    }
}