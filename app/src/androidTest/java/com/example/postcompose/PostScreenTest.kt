package com.example.postcompose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.postcompose.model.UIState
import com.example.postcompose.screens.PostsScreenImpl
import com.example.postcompose.viewmodel.PostsViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PostScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var mockViewModel: PostsViewModel
    lateinit var  mockOnClickPost: (Int) -> Unit



    @Before
    fun setup(){
        mockViewModel = mockk(relaxed = true)
        mockOnClickPost = mockk(relaxed = true)
    }


    @Test
    fun displaysProgressBarWhenLoading(){
        val uiState = UIState(isLoading = true)
        every { mockViewModel.uiState } returns MutableLiveData(uiState)
        every { mockViewModel.posts } returns MutableLiveData(emptyList())
        every { mockViewModel.fetchPosts() } returns Unit


        composeTestRule.setContent {
            PostsScreenImpl(
                onClickPost = mockOnClickPost,
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithTag("progressBar")
            .assertExists()
            .assertIsDisplayed()

        verify { mockViewModel.fetchPosts() }


    }



    @Test
    fun testPostsListIsDisplayed(){
        val uiState = UIState(isLoading = false, success = "Fetched posts successfully")
        every { mockViewModel.posts } returns MutableLiveData(TestData.mockPosts)
        every { mockViewModel.uiState } returns MutableLiveData(uiState)

        composeTestRule.setContent {
            PostsScreenImpl(
                onClickPost = mockOnClickPost,
                viewModel = mockViewModel
            )
        }

        composeTestRule.onNodeWithTag("postsList")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Title1")
            .assertExists()
            .assertIsDisplayed()

    }


    @Test
    fun testDisplaysErrorMessage(){
        val uiState = UIState(error = "Failed to fetch posts", isLoading = false)
        every { mockViewModel.uiState } returns MutableLiveData(uiState)

        composeTestRule.setContent {
            PostsScreenImpl(onClickPost = mockOnClickPost, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Failed to fetch posts")
            .assertExists()
            .assertIsDisplayed()
    }


    @Test
    fun testOnClickPostInvokesCallback(){
        val uiState = UIState(success = "Fetched post successfully", isLoading = false)
        every { mockViewModel.uiState } returns MutableLiveData(uiState)
        every {mockViewModel.posts} returns MutableLiveData(listOf(TestData.mockPost))

        composeTestRule.setContent {
            PostsScreenImpl(
                onClickPost = mockOnClickPost,
                viewModel  = mockViewModel
            )
        }

        composeTestRule.onNodeWithTag("clickablePost").performClick()

        verify { mockOnClickPost(1) }
    }

}