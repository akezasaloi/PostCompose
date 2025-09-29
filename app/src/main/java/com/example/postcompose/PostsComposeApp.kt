package com.example.postcompose

import android.app.Application
import com.example.postcompose.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PostsComposeApp: Application(){

    override fun onCreate(){
        super.onCreate()
        startKoin{
            androidContext(this@PostsComposeApp)
            modules(appModules)
        }
    }
    }
