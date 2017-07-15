package com.killer.sample.kotlin.realm.core.session

import com.killer.sample.kotlin.realm.model.realm.UserDO
import com.killer.sample.kotlin.realm.util.getBase64String

/**
 * Created by app on 7/8/17.
 */
class Session: ISession {
    var user: UserDO? = null

    private val sessionMemory = HashMap<String, Any>()

    override fun openSession() {
        if(sessionMemory.keys.size > 0) {
            sessionMemory.clear()
        }
    }

    override fun closeSession() {
        user = null
        sessionMemory.clear()
    }

    override fun isValid(): Boolean {
        return user != null
    }

    override fun getData(key: String): Any {
        return sessionMemory[key] != null
    }

    override fun putData(key: String, value: Any) {
        sessionMemory.put(key, value)
    }

    override fun removeData(key: String) {
        sessionMemory.remove(key)
    }

    override fun convertBase64AuthorizationData(): String {
        if (user != null) {
            return getBase64String(user!!.username + ":" + user!!.password)
        }
        return ""
    }
}