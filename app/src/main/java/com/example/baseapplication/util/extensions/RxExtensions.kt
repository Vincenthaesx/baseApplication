package com.example.baseapplication.util.extensions

import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.endWith(item: T): Observable<T> {
    return Observable.concatArray(this, Observable.just<T>(item))
}

fun View.onClick(disposable: CompositeDisposable, action: (View) -> Unit) {
    clicks()
        .throttleFirst(500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            action.invoke(this)
        }.addTo(disposable)
}