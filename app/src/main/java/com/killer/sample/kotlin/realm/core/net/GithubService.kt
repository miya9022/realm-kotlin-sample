package com.killer.sample.kotlin.realm.core.net

import com.killer.sample.kotlin.realm.model.realm.UserGithub
import com.killer.sample.kotlin.realm.model.realm.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by app on 7/3/17.
 */
interface GithubService {

    @GET("users/{username}")
    fun getGithubUser(@Header("Authorization")authorization: String, @Path("username") username: String): Observable<UserGithub>

    @GET("users/{username}/repos")
    fun getReposGithubUser(@Header("Authorization")authorization: String, @Path("username") username: String): Observable<List<Repository>>
}