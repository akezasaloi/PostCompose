package com.example.postcompose

import com.example.postscompose.api.ApiInterface
import com.example.postscompose.repository.PostsRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PostsRepositoryTest {


    lateinit var repository: PostsRepositoryImpl
    lateinit var mockApiInterface: ApiInterface

    @Before
    fun setup() {
        mockApiInterface = mockk(relaxed = true)
        repository = PostsRepositoryImpl(mockApiInterface)
    }


    @Test
    fun testFetchPosts() {
        runTest {
            //Given
            val expectedResponse = Response.success(TestData.mockPosts)
            coEvery { mockApiInterface.fetchPosts() } returns expectedResponse

            //When
            val result = repository.fetchPosts()

            //Then
            assertTrue(result.isSuccessful)
            assertEquals(result.body(), TestData.mockPosts)
        }
    }


    @Test
    fun testFetchPostById(){
        runTest {
            val expected = Response.success(TestData.mockPost)
            coEvery { mockApiInterface.fetchPostById(1) } returns expected


            val result = repository.fetchPostById(1)

            assertTrue(result.isSuccessful)
            assertEquals(result.body(), TestData.mockPost)
        }
    }




    @Test
    fun testFetchPostComments() {
        runTest {
            val expectedResponse = Response.success(TestData.mockComments)
            coEvery { mockApiInterface.fetchPostComments(1) } returns expectedResponse


            val result = repository.fetchPostComments(1)

            assertTrue(result.isSuccessful)
            assertEquals(result.body(), TestData.mockComments)

        }
    }
}


