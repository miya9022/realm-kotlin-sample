package com.killer.sample.kotlin.realm.core.session

/**
 * Created by app on 7/8/17.
 */
interface ISession {

    fun openSession()

    fun closeSession()

    fun isValid(): Boolean

    fun getData(key: String): Any

    fun putData(key: String, value: Any)

    fun removeData(key: String)

    fun convertBase64AuthorizationData(): String
}