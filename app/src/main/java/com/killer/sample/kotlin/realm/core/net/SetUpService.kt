package com.killer.sample.kotlin.realm.core.net

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Created by app on 7/3/17.
 */
class SetUpService @Inject constructor(){

    private val BASE_URL: String = "https://api.github.com/"

    var githubService: GithubService

    init {
        this.githubService = setupService()
    }

    private fun setupService(): GithubService {
        val gson = setupGson()
        val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build()
        return retrofit.create(GithubService::class.java)
    }

    private fun setupGson(): Gson {
        return GsonBuilder().setExclusionStrategies(object : ExclusionStrategy{
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaringClass == RealmObject::class.java
            }

            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }
        }).create()
    }
}