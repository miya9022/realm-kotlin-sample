package com.killer.sample.kotlin.realm.presenter

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.killer.sample.kotlin.realm.core.session.ISessionManager
import com.killer.sample.kotlin.realm.model.domain.AuthCredentials
import com.killer.sample.kotlin.realm.model.realm.UserDO
import com.killer.sample.kotlin.realm.view.login.LoginView
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.save
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by app on 7/8/17.
 */
class LoginPresenter @Inject constructor(val sessionManager: ISessionManager): MvpBasePresenter<LoginView>() {

    fun doLogin(credential: AuthCredentials) {
        val loginObservable = makeLoginObservable(credential)
        loginObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when {
                        it.isValid -> {
                            sessionManager.putUserOnSession(it)
                            view.loginSuccess()
                            view.startMainActivity()
                        }
                        else -> view.showError()
                    }
                }, {
                    error -> view.showError()
                })
    }

    private fun makeLoginObservable(credential: AuthCredentials): Observable<UserDO> {
        return Observable.create {
            val userDO = UserDO(credential.userName, credential.password, credential.rememberMe)
            val lastUserInStore = UserDO().queryAll().maxWith(Comparator { t1, t2 -> t1.id - t2.id })
            if (lastUserInStore != null) {
                when {
                    lastUserInStore.username == userDO.username -> userDO.id = lastUserInStore.id
                    else -> userDO.id = lastUserInStore.id + 1
                }
            }
            else userDO.id = 1
            userDO.save()
            it.onNext(userDO)
        }
    }

    fun checkUserLogin(): Boolean {
        return sessionManager.checkLogin()
    }
}