package cn.rubintry.rtmanager

import android.graphics.Rect
import android.view.View
import android.view.ViewGroup


var View.marginLeft: Int
    get() {
        val layoutParams = this.layoutParams
        return if (layoutParams is ViewGroup.MarginLayoutParams) {
            layoutParams.leftMargin
        } else {
            0
        }
    }
    set(value) {
        val layoutParam = this.layoutParams
        if(layoutParam is ViewGroup.MarginLayoutParams){
            layoutParam.leftMargin = value
        }
        this.layoutParams = layoutParam
    }


var View.marginTop: Int
    get() {
        val layoutParams = this.layoutParams
        return if (layoutParams is ViewGroup.MarginLayoutParams) {
            layoutParams.topMargin
        } else {
            0
        }
    }
    set(value) {
        val layoutParam = this.layoutParams
        if(layoutParam is ViewGroup.MarginLayoutParams){
            layoutParam.topMargin = value
        }
        this.layoutParams = layoutParam
    }


val View.visibleRect : Rect
    get() {
        val rect = Rect()
        this.getGlobalVisibleRect(rect)
        return rect
    }


val IRuntimeView.viewTag : String
    get() {
        return this.javaClass.canonicalName
    }