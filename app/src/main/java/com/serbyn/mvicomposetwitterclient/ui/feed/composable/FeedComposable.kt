@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.serbyn.mvicomposetwitterclient.ui.feed.composable

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.serbyn.mvicomposetwitterclient.R
import com.serbyn.mvicomposetwitterclient.ui.collectAsStateLifecycleAware
import com.serbyn.mvicomposetwitterclient.ui.feed.FeedContract
import com.serbyn.mvicomposetwitterclient.ui.feed.FeedViewModel
import com.serbyn.mvicomposetwitterclient.ui.feed.entity.TweetItem
import com.serbyn.mvicomposetwitterclient.ui.rememberFlow
import com.serbyn.mvicomposetwitterclient.ui.theme.MviComposeTwitterClientTheme
import com.serbyn.mvicomposetwitterclient.ui.theme.Purple700
import kotlinx.coroutines.launch

@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = { },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                contentColor = Color.DarkGray,
                onClick = {
                    viewModel.sendEvent(FeedContract.Event.NavigateToAddTweet)
                }) {
                Icon(Icons.Filled.Add, "")
            }
        },
        content = {
            Content(navController, viewModel)
        }
    )
}

@Composable
fun Content(
    navController: NavController,
    viewModel: FeedViewModel
) {
    val scope = rememberCoroutineScope()
    val effectsFlow = rememberFlow(viewModel.effects)
    val snackBarHostState = remember { SnackbarHostState() }
    scope.launch {
        effectsFlow.collect { effect ->
            when (effect) {
                is FeedContract.Effect.ShowToast -> showSnackBar(
                    effect.message,
                    snackBarHostState
                )
                FeedContract.Effect.AddTweetNavigated -> navController.navigate("addTweet")
            }
        }
    }

    val state: FeedContract.State by viewModel.uiState.collectAsStateLifecycleAware()
    when (val feedState = state.feedState) {
        is FeedContract.FeedState.Success -> {
            FeedContent(feedState.feedItems)
        }
        FeedContract.FeedState.Loading -> LoadingScreen()
        FeedContract.FeedState.Error -> ErrorScreen(error = "Error while loading feed screen!")
        FeedContract.FeedState.Idle -> EmptyScreen()
    }
}

suspend fun showSnackBar(message: String, snackBarHostState: SnackbarHostState) {
    snackBarHostState.showSnackbar(
        message = message
    )
}

@Composable
fun FeedContent(tweets: List<TweetItem>) {
    Surface(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(tweets) {
                for (item in tweets) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                    ) {
                        TweetItem(tweet = item)
                    }
                }
            }
        }
    }
}

@Composable
fun TweetItem(tweet: TweetItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://cdn.icon-icons.com/icons2/2438/PNG/512/boy_avatar_icon_148455.png")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_baseline_person_24),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = tweet.fullName
                    )
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = "@${tweet.firstName}"
                    )
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 5.dp,
                                bottom = 5.dp
                            ),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                            text = tweet.date
                        )
                    }
                }
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = tweet.message
                )
            }
        }
        Divider(color = Color.LightGray)
    }
}

@Composable
fun LoadingScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(color = Purple700)
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "Oops, $error!")
    }
}

@Composable
fun EmptyScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(text = "No items found!")
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    MviComposeTwitterClientTheme {
        FeedScreen(rememberNavController())
    }
}

private fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, "Clicked!!!!", Toast.LENGTH_SHORT).show()
}