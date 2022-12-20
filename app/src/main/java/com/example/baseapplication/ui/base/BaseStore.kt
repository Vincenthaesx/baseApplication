package com.example.baseapplication.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.example.baseapplication.model.repo.RepoContainer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseStore<A : Action, S> : ViewModel() {

    lateinit var repos: RepoContainer

    /**
     * The **Dispatcher** part in **Redux** model
     */
    private val mDispatcher by lazy {
        Dispatcher(this)
    }
    /**
     * Dispatches final states to View.
     *
     * **State** part in **Redux** model
     */
    private val newState by lazy {
        MutableLiveData<S>()
    }

    /**
     * **Reducer** part in **Redux** model
     */
    abstract fun Observable<A>.reduce(): Observable<S>

    fun subscribeDispatcher(dispatcher: Observable<A>, type: DispatchActions): Disposable {
        val scheduler = when (type) {
            DispatchActions.IO -> Schedulers.io()
            DispatchActions.DEFAULT -> AndroidSchedulers.mainThread()
        }
        return dispatcher.subscribe {action ->
            Observable.just(action)
                .reduce()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    newState.value = result
                    // release value of result to avoid resending newState
                    // In fact, this breaks the idea for what LiveData is created,
                    // but PublishSubject in RxJava doesn't care about the lifecycle of observer,
                    // so sometimes it leads crashes when observers receives notifs when view was
                    // destroyed.
                    // That's why I chose LiveData over PublishSubject in RxJava
                    // However, RxJava is still useful if we manage well
                    newState.value = null
                }
        }
    }

    fun subscribeObserver(owner: LifecycleOwner, render: (S) -> Unit) {
        newState.observe(owner, Observer {
            if (it == null) return@Observer
            render(it)
        })
    }

    fun dispatchAction(a: A) {
        mDispatcher.dispatchAction(a)
    }

    override fun onCleared() {
        super.onCleared()
        mDispatcher.clear()
    }

    class BaseStoreFactory(private val repo: RepoContainer) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val o = super.create(modelClass)
            if (o is BaseStore<*, *>) {
                o.repos = repo
            }
            return o
        }
    }

}

enum class DispatchActions {
    IO, DEFAULT
}

inline fun <reified T : BaseStore<*, *>> AppCompatActivity.getStore(repo: RepoContainer): T {
    return ViewModelProviders.of(this, BaseStore.BaseStoreFactory(repo))[T::class.java]
}