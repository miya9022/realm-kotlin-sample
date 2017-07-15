package com.killer.sample.kotlin.realm.model.realm

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by app on 7/2/17.
 */
@RealmClass
open class UserDO(): RealmObject() {
    @PrimaryKey
    open var id: Int = 0

    var username: String = ""

    var password: String = ""

    var rememberMe: Boolean = false

    constructor(username: String, password: String, rememberMe: Boolean) : this() {
        this.username = username
        this.password = password
        this.rememberMe = rememberMe
    }
}

@RealmClass
open class UserGithub: RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id:Int = 0

    @SerializedName("login")
    @Expose
    open var loginName: String? = null

    @SerializedName("avatar_url")
    @Expose
    open var avatarUrl: String? = null

    @SerializedName("name")
    @Expose
    open var name: String? = null

    @SerializedName("repos_url")
    @Expose
    open var reposUrl: String? = null

    @SerializedName("public_repos")
    @Expose
    open var publicRepos: Int? = null
}

@RealmClass
open class Repository: RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int = 0

    @SerializedName("owner")
    @Expose
    open var owner: UserGithub? = null

    @SerializedName("name")
    @Expose
    open var name: String? = null

    @SerializedName("full_name")
    @Expose
    open var fullName: String? = null

    @SerializedName("description")
    @Expose
    open var description: String? = null

    @SerializedName("html_url")
    @Expose
    open var url: String? = null

    @SerializedName("private")
    @Expose
    open var private: Boolean? = false

    @SerializedName("fork")
    @Expose
    open var fork: Boolean = false

    @SerializedName("size")
    @Expose
    open var size: Int? = null

    @SerializedName("language")
    @Expose
    open var language: String? = null

    @SerializedName("created_at")
    @Expose
    open var created: String? = null

    @SerializedName("pushed_at")
    @Expose
    open var pushed: String? = null
}