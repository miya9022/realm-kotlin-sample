package com.killer.sample.kotlin.realm.core.session

import android.app.Activity
import android.content.Context
import com.killer.sample.kotlin.realm.model.realm.UserDO
import com.killer.sample.kotlin.realm.view.login.LoginActivity
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import javax.inject.Singleton

/**
 * Created by app on 7/8/17.
 */
@Singleton
class SessionManager(context: Context): ISessionManager {

    override var session: Session = Session()

    override var username: String = ""

    private val context: Context

    init {
        this.context = context.applicationContext
    }

    override fun putUserOnSession(user: UserDO) {
        session.openSession()
        session.user = user
        username = user.username
    }

    override fun checkLogin(): Boolean {
        if(!isUserLogged()) {
            redirectToLoginActivity()
            return false
        }
        return true
    }

    override fun checkLogin(activity: Activity): Boolean {
        val result = checkLogin()
        if (!result) {
            activity.finish()
        }
        return result
    }

    override fun logout() {
        closeSession()
        redirectToLoginActivity()
    }

    override fun defaultAuthorization(): String {
        return "Basic " + session.convertBase64AuthorizationData()
    }

    fun isUserLogged(): Boolean {
        return session.isValid()
    }

    fun redirectToLoginActivity() {
        context.startActivity(context.intentFor<LoginActivity>().clearTop().newTask())
    }

    private fun closeSession() {
        session.closeSession()
        username = ""
    }
}