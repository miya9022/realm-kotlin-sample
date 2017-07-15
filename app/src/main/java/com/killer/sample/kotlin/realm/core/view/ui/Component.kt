package com.killer.sample.kotlin.realm.core.view.ui

import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * Created by app on 7/14/17.
 */
class RecyclerItemTouchListener(private var clickListener: ClickListener, rv: RecyclerView) : RecyclerView.OnItemTouchListener {
    private var gestureDetector: GestureDetector

    init {
        this.gestureDetector = GestureDetector(rv.context, ItemGestureListener(rv))
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
        // do nothing
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val child: View? = rv!!.findChildViewUnder(e!!.getX(), e.getY())
        if (child != null) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // do nothing
    }

    inner class ItemGestureListener(val rv: RecyclerView): GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }

        override fun onLongPress(e: MotionEvent?) {
            val child: View? = rv.findChildViewUnder(e!!.getX(), e.getY())
            if (child != null) {
                clickListener.onLongClick(child, rv.getChildAdapterPosition(child))
            }
        }
    }
}

interface ClickListener {
    fun onClick(view: View, position: Int)
    fun onLongClick(view: View, position: Int)
}