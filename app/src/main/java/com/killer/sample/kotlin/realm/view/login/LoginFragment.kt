package com.killer.sample.kotlin.realm.view.login

import com.killer.sample.kotlin.realm.app.MyApplication
import com.killer.sample.kotlin.realm.core.mvp.BaseViewMvpFragment
import com.killer.sample.kotlin.realm.presenter.LoginPresenter
import org.jetbrains.anko.AnkoContext

/**
 * Created by app on 7/14/17.
 */
class LoginFragment: BaseViewMvpFragment<LoginView, LoginPresenter>() {
    override fun onCreateMvpView() = LoginView()

    override fun onCreateView() = view.createView(AnkoContext.Companion.create(this.activity, this.activity))

    override fun createPresenter(): LoginPresenter {
        val presenter = MyApplication.applicationComponent.loginPres()
        view.presenter = presenter
        return presenter
    }

    override fun onProExecution() {
        when {
            presenter.checkUserLogin() -> view.startMainActivity()
        }
    }
}