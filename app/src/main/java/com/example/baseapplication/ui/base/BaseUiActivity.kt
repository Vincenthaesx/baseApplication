package com.example.baseapplication.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.baseapplication.model.repo.RepoContainer
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

abstract class BaseUiActivity<A : Action, S, T : BaseStore<A, S>> : AppCompatActivity() {

    protected val repo by inject<RepoContainer>()

    protected val disposable by lazy { CompositeDisposable() }

    private val store by lazy { store() }

    abstract fun store(): T

    abstract fun render(state: S)

    fun dispatchAction(a: A) {
        store.dispatchAction(a)
    }

    @LayoutRes
    abstract fun layout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout())
        store.subscribeObserver(this, ::render)
    }
}