package com.example.baseapplication.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.baseapplication.model.repo.RepoContainer
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

@Suppress("UNCHECKED_CAST")
abstract class BaseUiFragment<A : Action, S, T : BaseStore<A, S>> : Fragment() {

    protected val repo by inject<RepoContainer>()

    protected val disposable by lazy { CompositeDisposable() }

    private val store by lazy { store() }

    open fun store() : T = (requireActivity() as BaseUiActivity<*, *, *>).store() as T

    open fun render(state: S) {}

    fun dispatchAction(a: A) {
        store.dispatchAction(a)
    }

    @LayoutRes
    abstract fun layout(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.subscribeObserver(this, ::render)
    }

}