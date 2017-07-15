package com.killer.sample.kotlin.realm.view.login

import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by app on 7/8/17.
 */
interface LoginMvpView: MvpView {
    fun loginSuccess()

    fun startMainActivity()

    fun showError()
}