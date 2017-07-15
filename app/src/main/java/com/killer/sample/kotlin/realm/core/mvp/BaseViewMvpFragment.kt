package com.killer.sample.kotlin.realm.core.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

/**
 * Created by app on 7/14/17.
 */
abstract class BaseViewMvpFragment<V: MvpView, P: MvpPresenter<V>>: MvpFragment<V, P>() {

    protected lateinit var view: V

    abstract fun onCreateMvpView(): V

    abstract fun onCreateView(): View

    abstract fun onProExecution()

    override fun getMvpView(): V {
        return view
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = onCreateMvpView()
        return onCreateView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onProExecution()
    }
}