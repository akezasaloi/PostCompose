package com.example.postscompose.api

import com.example.postcompose.model.Comment
import com.example.postcompose.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/posts")
    suspend fun fetchPosts(): Response<List<Post>>

    @GET("/posts/{postID}")
    suspend fun fetchPostById(@Path("postID") postID: Int): Response<Post>

    @GET("/posts/{postID}/comments")
    suspend fun fetchPostComments(@Path("postID") postID: Int): Response<List<Comment>>

}