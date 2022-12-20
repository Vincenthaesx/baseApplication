package com.example.baseapplication.global

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.baseapplication.di.components
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        globalContext = this


        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            koin.loadModules(components)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var globalContext: Context

        private val sharePreference: SharedPreferences by lazy {
            globalContext.getSharedPreferences(SHARE_PREFERENCE, Context.MODE_PRIVATE)
        }

        val container by lazy { SPContainer(sharePreference) }

        private const val SHARE_PREFERENCE = "com.base.share_preference"
    }
}

class SPContainer(sp: SharedPreferences) {
    companion object {
   //     const val EXEMPLE = "exemple"
    }

   // var exemple: String? by SharePreferenceDelegate(sp, null, EXEMPLE, String::class.java)
}