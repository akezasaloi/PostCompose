package com.example.postcompose

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.postcompose.viewmodel.PostsViewModel
import com.example.postscompose.repository.PostsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.bytebuddy.matcher.ElementMatchers.returns
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response



@OptIn(ExperimentalCoroutinesApi::class)
class PostViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PostsViewModel
    private lateinit var mockRepository: PostsRepository
    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
        viewModel = PostsViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun fetchPostsSuccess() {
        runTest {
            coEvery { mockRepository.fetchPosts() } returns Response.success(TestData.mockPosts)
            viewModel.fetchPosts()
            assertEquals(TestData.mockPosts, viewModel.posts.value)
            assertEquals(viewModel.uiState.value?.success, "Fetch posts successfully")
            assertEquals(viewModel.uiState.value?.isLoading, false)
            assertNull(viewModel.uiState.value?.error)
        }
    }


    @Test
    fun `Should display error message`() {
        runTest {
            coEvery { mockRepository.fetchPosts() } returns TestData.errorResponse
            viewModel.fetchPosts()
            assertNull(viewModel.posts.value)
            assertNull(viewModel.uiState.value?.success)
            assertNotNull(viewModel.uiState.value?.error)
            assertEquals(viewModel.uiState.value?.isLoading, false)

        }
    }


    @Test
    fun testFetchPostById() {
        runTest {
            coEvery { mockRepository.fetchPostById(2) } returns Response.success(TestData.mockPost)
            viewModel.fetchPostById(2)
            assertEquals(TestData.mockPost, viewModel.post.value)
            assertEquals(viewModel.uiState.value?.success, "Fetch post successfully")
            assertEquals(viewModel.uiState.value?.isLoading, false)
            assertNull(viewModel.uiState.value?.error)
        }
    }


//    @Test
//    fun `Should display error message when fetching post`() {
//        runTest {
//            coEvery { mockRepository.fetchPostById(2) } returns TestData.errorResponse
//            viewModel.fetchPostById(2)
//            assertNull(viewModel.post.value)
//            assertNull(viewModel.uiState.value?.success)
//            assertNotNull(viewModel.uiState.value?.error)
//            assertEquals(viewModel.uiState.value?.isLoading, false)
//        }
//    }


    @Test
    fun testFetchPostComments() {
        runTest {
            coEvery { mockRepository.fetchPostComments(4) } returns Response.success(TestData.mockComments)
        viewModel.fetchPostComments(4)
        assertEquals(TestData.mockComments, viewModel.comments.value)
        assertEquals(viewModel.uiState.value?.success, "Fetch post comments successfully")
        assertEquals(viewModel.uiState.value?.isLoading, false)
        assertNull(viewModel.uiState.value?.error)
        }
    }
}