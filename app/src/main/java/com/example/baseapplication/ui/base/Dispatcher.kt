package com.example.baseapplication.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject

/**
 * A transporter which dispatches all actions to [BaseStore] to handle
 */
class Dispatcher<A : Action, S>(private val store: BaseStore<A, S>) {

    private val disposable by lazy { CompositeDisposable() }
    /**
     * Dispatcher on IO thread
     */
    private val ioInternalDispatcher by lazy {
        PublishSubject.create<A>().apply {
            store.subscribeDispatcher(this, DispatchActions.IO).addTo(disposable)
        }
    }
    /**
     * Dispatcher on default thread (main thread in general)
     */
    private val defInternalDispatcher by lazy {
        PublishSubject.create<A>().apply {
            store.subscribeDispatcher(this, DispatchActions.DEFAULT).addTo(disposable)
        }
    }

    fun dispatchAction(a: A) {
        when (a) {
            is IOAction -> {
                ioInternalDispatcher.onNext(a)
            }
            is DefAction, is NavAction -> {
                defInternalDispatcher.onNext(a)
            }
        }

    }

    fun clear() {
        disposable.clear()
    }
}