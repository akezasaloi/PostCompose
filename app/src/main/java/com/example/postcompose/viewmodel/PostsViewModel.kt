package com.example.postcompose.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postcompose.model.Comment
import com.example.postcompose.model.Post
import com.example.postscompose.model.UIState
import com.example.postscompose.repository.PostsRepository
import com.example.postscompose.repository.PostsRepositoryImpl
import kotlinx.coroutines.launch

class PostsViewModel(val postsRepository: PostsRepository): ViewModel() {


    val posts = MutableLiveData<List<Post>>()

    val uiState = MutableLiveData(UIState())
    val post = MutableLiveData<Post>()
    val comments = MutableLiveData<List<Comment>>()

    fun fetchPosts(){
        viewModelScope.launch {
            uiState.value = uiState.value?.copy(isLoading = true)
            val response = postsRepository.fetchPosts()
            if (response.isSuccessful){
                uiState.value = uiState.value?.copy(success = "Fetch posts successfully", isLoading = false)
                posts.postValue( response.body())
            }
            else{
                uiState.value = uiState.value?.copy(error = response.errorBody()?.string(), isLoading = false)

            }
        }
    }

    fun fetchPostById(postId: Int) {
        viewModelScope.launch {
            uiState.value = uiState.value?.copy(isLoading = true)
            val response = postsRepository.fetchPostById(postId)
            if (response.isSuccessful) {
                uiState.value = uiState.value?.copy(
                    success = "Fetch post by ID successfully",
                    isLoading = false
                )
                post.postValue(response.body())
            } else {
                uiState.value =
                    uiState.value?.copy(isLoading = false, error = response.errorBody()?.string())
            }

        }
    }

    fun fetchPostComments(postId: Int) {
        viewModelScope.launch {
            uiState.value = uiState.value?.copy(isLoading = true)
            val response = postsRepository.fetchPostComments(postId)
            if (response.isSuccessful) {
                uiState.value = uiState.value?.copy(
                    isLoading = false,
                    success = "Fetch post comments successfully"
                )
                comments.postValue(response.body())
                } else {
                uiState.value =
                    uiState.value?.copy(isLoading =
                        false, error = response.errorBody()?.string())
            }
        }


    }
}