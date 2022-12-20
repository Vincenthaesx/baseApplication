package com.example.baseapplication.ui.main

import com.example.baseapplication.ui.base.BaseStore
import com.example.baseapplication.ui.main.MainAction
import com.example.baseapplication.ui.main.MainErrorType
import com.example.baseapplication.ui.main.MainState
import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType

class MainStore : BaseStore<MainAction, MainState>() {

    override fun Observable<MainAction>.reduce(): Observable<MainState> {
        return Observable.mergeArray()
  //      return Observable.mergeArray().onErrorReturn {
  //          MainErrorType.DefaultError(it.message).get()
  //      }
    }

    // ---------------------------

    // ------------------
    // GENERAL
    // ------------------



}