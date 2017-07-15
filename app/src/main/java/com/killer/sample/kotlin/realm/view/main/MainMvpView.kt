package com.killer.sample.kotlin.realm.view.main

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.killer.sample.kotlin.realm.model.realm.Repository
import com.killer.sample.kotlin.realm.model.realm.UserGithub

/**
 * Created by app on 7/5/17.
 */
interface MainMvpView: MvpView {
    fun insertRepo(repo: Repository)

    fun updateUserInfo(it: UserGithub?)

    fun noticeError(error: Throwable)

    fun showTaskCompleted()
}