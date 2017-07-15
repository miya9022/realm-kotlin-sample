package com.killer.sample.kotlin.realm.core.session

import android.app.Activity
import com.killer.sample.kotlin.realm.model.realm.UserDO

/**
 * Created by app on 7/8/17.
 */
interface ISessionManager {

    fun checkLogin(): Boolean

    fun checkLogin(activity: Activity): Boolean

    var session: Session

    var username: String

    fun logout()

    fun putUserOnSession(user: UserDO)

    fun defaultAuthorization(): String
}