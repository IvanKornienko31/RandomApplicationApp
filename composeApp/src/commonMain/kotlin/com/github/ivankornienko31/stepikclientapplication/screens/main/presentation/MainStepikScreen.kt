package com.github.ivankornienko31.stepikclientapplication.screens.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.github.ivankornienko31.stepikclientapplication.screens.main.data.remote.RedditPostModel
import com.github.ivankornienko31.stepikclientapplication.themes.CustomModifiers
import com.github.ivankornienko31.stepikclientapplication.themes.CustomTextStyles
import com.github.ivankornienko31.stepikclientapplication.themes.randomColor

/**
 * Экран успешной авторизации.
 */

@Composable
fun MainStepikScreen(
    viewModel: MainViewModel = viewModel { MainViewModel() }
) {
    Scaffold(
        modifier = CustomModifiers.scaffoldModifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text("Will be added soon =)")
        }
    }
}

@Deprecated(
    message = "WARNING! This function will be replaced by Stepik analogue at the next update",
    replaceWith = ReplaceWith(
        expression = "MainStepikScreen()"
    )
)
@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    Scaffold { innerPadding ->
        val posts by viewModel.uiState.collectAsState()

        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (posts == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = posts.orEmpty(),
                        key = { post -> post.id }
                    ) { post ->
                        RedditPostItem(post)
                    }

                    item {
                        AsyncImage(
                            model = "https://cdn.stepik.net/media/cache/images/courses/275883/cover_VOPaIJE/69dfa941ef101e6d5e4b70d2aa37fb8d.jpg",
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Deprecated(
    message = "WARNING! This function will be removed at the next update",
)
@Composable
fun RedditPostItem(post: RedditPostModel) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            RedditPostHeader(post)
            Spacer(modifier = Modifier.height(8.dp))

            RedditPostTitle(post)
            Spacer(modifier = Modifier.height(8.dp))

            RedditPostContent(post)
            Spacer(modifier = Modifier.height(4.dp))

            RedditPostActions(post)
        }
    }
}

@Deprecated(
    message = "WARNING! This function will be removed at the next update",
)
@Composable
fun RedditPostHeader(post: RedditPostModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${post.subreddit} • u/${post.author} • ${post.hoursAgo}h",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )
    }
}

@Deprecated(
    message = "WARNING! This function will be removed at the next update",
)
@Composable
fun RedditPostTitle(post: RedditPostModel) {
    Text(
        text = post.title,
        style = CustomTextStyles.mainTextStyle.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )
    )
}

@Deprecated(
    message = "WARNING! This function will be removed at the next update",
)
@Composable
fun RedditPostContent(post: RedditPostModel) {
    if (post.contentText != null) {
        Text(
            text = post.contentText,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
    } else {
        // Placeholder for Image content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Image Content Placeholder", color = Color.DarkGray)
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Deprecated(
    message = "WARNING! This function will be removed at the next update",
)
@Composable
fun RedditPostActions(post: RedditPostModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        PostAction(
            icon = Icons.Default.ArrowUpward,
            text = post.likesCount.toString()
        )
        Spacer(modifier = Modifier.width(16.dp))
        PostAction(
            icon = Icons.Default.ChatBubbleOutline,
            text = post.commentsCount.toString()
        )
        Spacer(modifier = Modifier.width(16.dp))
        PostAction(
            icon = Icons.Default.Share,
            text = "Share"
        )
    }
}

@Deprecated(
    message = "WARNING! This function will be removed at the next update",
)
@Composable
fun PostAction(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(randomColor, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
    }
}
