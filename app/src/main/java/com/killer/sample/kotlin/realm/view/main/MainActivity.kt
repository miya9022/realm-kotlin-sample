package com.killer.sample.kotlin.realm.view.main

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.killer.sample.kotlin.realm.app.MyApplication
import com.killer.sample.kotlin.realm.presenter.MainPresenter
import org.jetbrains.anko.setContentView

class MainActivity : MvpActivity<MainView, MainPresenter>() {
    private var view: MainView = MainView()

    override fun createPresenter(): MainPresenter {
        val presenter = MyApplication.applicationComponent.mainPres()
        view.presenter = presenter
        return presenter
    }

    override fun getMvpView(): MainView {
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view.setContentView(this)
    }
}