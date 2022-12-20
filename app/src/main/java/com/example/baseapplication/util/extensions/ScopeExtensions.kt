package com.example.baseapplication.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.baseapplication.R

val Fragment.contextFragment
    get() = requireContext()

/*------------------------------------Activity----------------------------------------*/

fun FragmentActivity.replaceFragment(
    @IdRes containerViewId: Int, fragment: Fragment,
    addBackStack: Boolean = false,
    callback: (() -> Unit)? = null
) {
    supportFragmentManager.beginTransaction()
        .replace(containerViewId, fragment)
        .addToBackStack(addBackStack)
        .commit()
    supportFragmentManager.executePendingTransactions()
    callback?.invoke()
}

const val RIGHT_LEFT = 0
const val BOTTOM_TOP = 1

fun FragmentActivity.addAndHideFragment(
    @IdRes containerViewId: Int, fragment: Fragment,
    anim: Boolean = true,
    direction: Int = RIGHT_LEFT
) {
    supportFragmentManager.beginTransaction()
        .setAnim(anim, direction)
        .safeHide(supportFragmentManager.currentFragment())
        .add(containerViewId, fragment)
        .addToBackStack(null)
        .commit()
}

private fun FragmentTransaction.setAnim(anim: Boolean, direction: Int = RIGHT_LEFT): FragmentTransaction {
    return if (anim) {
        when (direction) {
            RIGHT_LEFT -> setCustomAnimations(
                R.anim.slide_in_right_left,
                R.anim.empty,
                R.anim.empty,
                R.anim.slide_out_right_left
            )
            BOTTOM_TOP -> setCustomAnimations(
                R.anim.slide_in_bot_top,
                R.anim.empty,
                R.anim.empty,
                R.anim.slide_out_bot_top
            )
            else -> this
        }


    } else this
}

inline fun <reified T : Activity> Activity.singleStart(extra: Bundle? = null) {
    intent<T> {
        flag { Intent.FLAG_ACTIVITY_CLEAR_TASK }
        flag { Intent.FLAG_ACTIVITY_NEW_TASK }
        extra?.let {
            bundle { it }
        }
    }.start(this)
}

/*------------------------------------Fragment----------------------------------------*/


fun FragmentTransaction.addToBackStack(addBackStack: Boolean): FragmentTransaction {
    return if (addBackStack) addToBackStack(null) else this
}

fun Fragment.checkPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(requireContext(), permission) == PERMISSION_GRANTED
}

/*------------------------------------Group of functions for Intent-creation DSL---------------------------------------*/

inline fun <reified T : Activity> Activity.intent(config: Intent.() -> Unit): Intent {
    return Intent(this, T::class.java).apply {
        this.config()
    }
}

fun Intent.flag(set: () -> Int) {
    addFlags(set())
}

fun Intent.bundle(set: () -> Bundle) {
    putExtras(set())
}

fun Intent.start(context: Context) {
    context.startActivity(this)
}