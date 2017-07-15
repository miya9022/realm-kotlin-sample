package com.killer.sample.kotlin.realm.di.component

import android.app.Application
import com.killer.sample.kotlin.realm.app.MyApplication
import com.killer.sample.kotlin.realm.core.net.SetUpService
import com.killer.sample.kotlin.realm.core.session.ISessionManager
import com.killer.sample.kotlin.realm.di.module.ApplicationModule
import com.killer.sample.kotlin.realm.presenter.LoginPresenter
import com.killer.sample.kotlin.realm.presenter.MainPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by app on 7/9/17.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun application(): Application

    fun setupService(): SetUpService

    fun sessionManager(): ISessionManager

    @Singleton
    fun loginPres(): LoginPresenter

    @Singleton
    fun mainPres(): MainPresenter

    fun inject(application: MyApplication)
}