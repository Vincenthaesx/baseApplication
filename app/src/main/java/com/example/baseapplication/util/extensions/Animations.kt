package com.example.baseapplication.util.extensions

import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import com.example.baseapplication.R

fun View.scaleEffectOnClick() {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val anim = AnimationUtils.loadAnimation(context, R.anim.scale_down)
                anim.fillAfter = true
                v.startAnimation(anim)
            }
            MotionEvent.ACTION_UP -> {
                val anim = AnimationUtils.loadAnimation(context, R.anim.scale_up)
                anim.fillAfter = true
                v.startAnimation(anim)
            }
        }
        false
    }
}