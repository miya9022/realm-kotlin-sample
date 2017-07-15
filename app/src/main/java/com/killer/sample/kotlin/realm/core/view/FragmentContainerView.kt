package com.killer.sample.kotlin.realm.core.view

import com.killer.sample.kotlin.realm.view.login.LoginActivity
import com.killer.sample.kotlin.realm.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

/**
 * Created by app on 7/8/17.
 */
class FragmentContainerView: AnkoComponent<LoginActivity> {
    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        frameLayout {
            id = R.id.login_container
            lparams(width = matchParent, height = matchParent)
        }
    }
}