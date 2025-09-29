package com.example.postcompose

import com.example.postcompose.model.Comment
import com.example.postcompose.model.Post
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object TestData {

    val mockPosts = listOf(
        Post(
            userId = 1,
            id = 1,
            title = "Title1",
            body = "body1"
        ),
        Post(
            userId = 2,
            id = 2,
            title = "Title2",
            body = "body2"
        )
    )

    val mockPost = Post(
        userId = 1,
        id = 1,
        title = "Title1",
        body = "body1"
    )

    val mockComments = listOf(
        Comment(
            id = 1,
            postId = 1,
            name = "Abed",
            email = "Abed45@gmail.com",
            body = "Comment body1"
        ),
        Comment(
            id = 2,
            postId = 1,
            name = "Rafaela",
            email = "rafaela2027@gmail.com",
            body = "Comment body2"
        )
    )

    val errorResponse = Response.error<List<Post>>(
        404,
        "{\"error\": \"Not found\"}".toResponseBody()
    )
}


