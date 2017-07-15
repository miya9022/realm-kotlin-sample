package com.killer.sample.kotlin.realm.app

import android.app.Application
import com.killer.sample.kotlin.realm.di.component.ApplicationComponent
import com.killer.sample.kotlin.realm.di.component.DaggerApplicationComponent
import com.killer.sample.kotlin.realm.di.module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by app on 7/4/17.
 */
class MyApplication: Application() {

    companion object {
        @JvmStatic lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
        initRealm()
    }

    fun initDaggerComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }

    fun initRealm() {
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }
}