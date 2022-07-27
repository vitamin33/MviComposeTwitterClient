package com.serbyn.mvicomposetwitterclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serbyn.mvicomposetwitterclient.databinding.ContentMainBinding
import com.serbyn.mvicomposetwitterclient.ui.feed.composable.FeedScreen
import com.serbyn.mvicomposetwitterclient.ui.theme.MviComposeTwitterClientTheme
import com.serbyn.mvicomposetwitterclient.ui.tweet.composable.AddTweetScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MviComposeTwitterClientTheme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {
        // setup the navController this way
        val navController = rememberNavController()

        NavHost(
            navController, // the navController created above
            startDestination = "feed" // start destination variable needs to match one of the composable screen routes below
        ) {
            composable("feed") { FeedScreen(navController) }
            composable("addTweet") { AddTweetScreen(navController) }
        }
    }
}