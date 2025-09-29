package com.example.postcompose.model

data class Comment (
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)


//@SerializedName("postId") val postID: Int, if your api returns snake case or a different case than your data fields