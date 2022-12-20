package com.example.baseapplication.di


import com.example.baseapplication.model.repo.RepoContainer
import com.example.baseapplication.model.repo.repoImplementations.MainManager
import get
import org.koin.dsl.module
import io.reactivex.schedulers.Schedulers
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


private val model = module {

    single<RepoContainer> {
        object : RepoContainer {
            override val mainRepo = MainManager()
        }
    }
}

val components = listOf(model)
