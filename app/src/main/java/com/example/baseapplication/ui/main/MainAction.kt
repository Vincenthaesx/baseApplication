package com.example.baseapplication.ui.main

import com.example.baseapplication.ui.base.Action
import com.example.baseapplication.ui.base.DefAction
import com.example.baseapplication.ui.base.IOAction


sealed class MainAction : Action {

    sealed class IO : MainAction(), IOAction {


    }

    sealed class Default : MainAction(), DefAction {


    }
}

sealed class MainState {


    // -----------------------------------

    class Loading(val isLoading: Boolean) : MainState()

    class Error(val error: MainErrorType) : MainState()
}

sealed class MainErrorType(val message: String? = null) {
    fun get(): MainState.Error = MainState.Error(this)

    class DefaultError(message: String?) : MainErrorType(message)
}