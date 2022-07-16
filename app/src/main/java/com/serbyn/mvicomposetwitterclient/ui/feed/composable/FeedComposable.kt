@file:OptIn(ExperimentalMaterial3Api::class)

package com.serbyn.mvicomposetwitterclient.ui.feed.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.serbyn.mvicomposetwitterclient.R
import com.serbyn.mvicomposetwitterclient.ui.feed.entity.TweetItem
import com.serbyn.mvicomposetwitterclient.ui.theme.MviComposeTwitterClientTheme
import com.serbyn.mvicomposetwitterclient.ui.theme.Purple700

@Composable
fun FeedScreen(tweets: List<TweetItem>) {
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
                            modifier = Modifier.padding(5.dp),
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


@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    val feedItems = listOf(
        TweetItem("1", "Jack", "Walton", "All is good!", "11-10-2022"),
        TweetItem("1", "Jack", "Walton", "All is good!", "11-10-2022"),
        TweetItem("1", "Jack", "Walton", "All is good!", "11-10-2022")
    )
    MviComposeTwitterClientTheme {
        FeedScreen(tweets = feedItems)
    }
}