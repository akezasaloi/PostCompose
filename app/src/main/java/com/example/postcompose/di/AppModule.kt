package com.example.postcompose.di

import com.example.postcompose.viewmodel.PostsViewModel
import com.example.postscompose.api.ApiInterface
import com.example.postscompose.repository.PostsRepository
import com.example.postscompose.repository.PostsRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single<ApiInterface> {
        get<Retrofit>().create(ApiInterface::class.java)
    }
}
val repositoryModule = module{
    single<PostsRepository> {
        PostsRepositoryImpl(get())
    }
}


val viewModule = module{
    single {
        PostsViewModel(get())
    }
}

val appModules = listOf(networkModule, repositoryModule, viewModule)