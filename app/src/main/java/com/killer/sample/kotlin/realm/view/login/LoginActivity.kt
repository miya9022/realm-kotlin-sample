package com.killer.sample.kotlin.realm.view.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.killer.sample.kotlin.realm.R
import com.killer.sample.kotlin.realm.core.view.FragmentContainerView
import org.jetbrains.anko.setContentView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentContainerView().setContentView(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.login_container, LoginFragment())
                    .commit()
        }
    }
}
