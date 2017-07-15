package com.killer.sample.kotlin.realm.util

import android.content.Context
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.killer.sample.kotlin.realm.model.realm.Repository

/**
 * Created by app on 7/6/17.
 */
fun hideKeyboard(view: View?): Boolean {

    if (view == null) throw NullPointerException("View is null!")

    try {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: Exception) {
        return false
    }

    return true
}

fun getBase64String(value: String): String {
    return Base64.encodeToString(value.toByteArray(charset = Charsets.UTF_8), Base64.NO_WRAP)
}

fun QuickSort(list: List<Repository>): List<Repository>? {
    val result: ArrayList<Repository> = ArrayList()
    if (list.size <= 1) {
        return list
    }

    val position: Int = list.size/2
    val pivot: Repository = list[position]

    result.addAll(QuickSort(list.filter { it.name!!.compareTo(pivot.name!!, true) < 0 })!!)
    result.addAll(QuickSort(list.filter { it.name!!.compareTo(pivot.name!!, true) >= 0 })!!)
    return result
}
