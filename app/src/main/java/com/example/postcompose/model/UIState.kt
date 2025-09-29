package com.example.postscompose.model

data class UIState(
    val isLoading: Boolean=false,
    val success: String?= null,
    val error: String?= null
)