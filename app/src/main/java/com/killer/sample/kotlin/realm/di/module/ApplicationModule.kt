package com.killer.sample.kotlin.realm.di.module

import android.app.Application
import com.killer.sample.kotlin.realm.core.net.SetUpService
import com.killer.sample.kotlin.realm.core.session.ISessionManager
import com.killer.sample.kotlin.realm.core.session.SessionManager
import com.killer.sample.kotlin.realm.presenter.LoginPresenter
import com.killer.sample.kotlin.realm.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by app on 7/9/17.
 */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideSetupService(): SetUpService = SetUpService()

    @Provides
    @Singleton
    fun provideSessionManager(): ISessionManager = SessionManager(application)

    @Provides
    @Singleton
    fun provideLoginPresenter(sessionManager: ISessionManager): LoginPresenter = LoginPresenter(sessionManager)

    @Provides
    @Singleton
    fun provideMainPresenter(service: SetUpService, sessionManager: ISessionManager): MainPresenter
            = MainPresenter(service, sessionManager)
}