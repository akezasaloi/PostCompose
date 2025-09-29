package com.example.postscompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.postcompose.model.Post
import com.example.postcompose.viewmodel.PostsViewModel
import com.example.postscompose.model.UIState
import org.koin.androidx.compose.koinViewModel


@Composable

fun PostsScreen(onClickPost: (Int)-> Unit,
                viewModel: PostsViewModel = koinViewModel()){
    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    val posts by viewModel.posts.observeAsState()

    val uiState by viewModel.uiState.observeAsState(UIState())

    when {
        uiState.isLoading ->{
            Box(Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }

        }
        uiState.success != null ->{
            if (posts != null){
                LazyColumn (verticalArrangement = Arrangement.spacedBy(16.dp)){
                    items(posts!!){post -> PostCard(post, onClickPost) }
                }
            }

        }
        uiState.error != null ->{
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment= Alignment.Center){
                Text(text = uiState.error.toString())
            }

        }
    }
}

@Composable


fun PostCard(post: Post, onClickPost: (Int) -> Unit) {
    Card(
        onClick = { onClickPost(post.id) },
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable

fun PostCardPreview(){
    val post = Post(
        userId = 1,
        id= 2,
        title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        body = "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
    )

    PostCard(post = post, {})
}