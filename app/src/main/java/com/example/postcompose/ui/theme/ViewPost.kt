package com.example.postcompose.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.postcompose.viewmodel.PostsViewModel

@Composable
fun ViewPostScreen(postId: Int,
    postViewModel: PostsViewModel = viewModel())
{
    LaunchedEffect(Unit) {
        postViewModel.fetchPostById(postId)
        postViewModel.fetchPostComments(postId)
    }
    val post by postViewModel.post.observeAsState()
    val comments by postViewModel.comments.observeAsState()

    Column(Modifier.fillMaxSize().padding((16.dp))) {
        post?.let{
            Text(text = it.title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(text = it.body)
        }
        comments?.let {
            Text(text = "COMMENTS", fontWeight = FontWeight.Bold)
            //Display comments list here
        }

    }
}

