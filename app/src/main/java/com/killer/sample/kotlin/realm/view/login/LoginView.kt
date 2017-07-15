package com.killer.sample.kotlin.realm.view.login

import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.LinearLayout
import com.hkm.ui.processbutton.iml.ActionProcessButton
import com.killer.sample.kotlin.realm.R
import com.killer.sample.kotlin.realm.core.anko.materialEditText
import com.killer.sample.kotlin.realm.core.anko.string
import com.killer.sample.kotlin.realm.model.domain.AuthCredentials
import com.killer.sample.kotlin.realm.presenter.LoginPresenter
import com.killer.sample.kotlin.realm.util.hideKeyboard
import com.killer.sample.kotlin.realm.view.main.MainActivity
import com.rengwuxian.materialedittext.MaterialEditText
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by app on 7/14/17.
 */
class LoginView: AnkoComponent<FragmentActivity>, LoginMvpView {

    lateinit var presenter: LoginPresenter

    lateinit var ankoView: AnkoContext<FragmentActivity>

    override fun createView(ui: AnkoContext<FragmentActivity>) = with(ui) {
        ankoView = ui

        linearLayout {
            this.gravity = Gravity.CENTER
            lparams(width = matchParent, height = matchParent) {
                orientation = LinearLayout.VERTICAL
                this.gravity = Gravity.CENTER
            }

            scrollView {
                lparams(width = matchParent, height = wrapContent)

                linearLayout {
                    this.gravity = Gravity.CENTER
                    lparams(width = matchParent, height = matchParent) {
                        orientation = LinearLayout.VERTICAL
                    }

                    linearLayout {
                        id = R.id.login_form
                        lparams(width = dip(300), height = wrapContent) {
                            background = ContextCompat.getDrawable(ctx, android.R.color.white)
                            orientation = LinearLayout.VERTICAL
                            this.gravity = Gravity.CENTER
                            padding = dip(16)
                            clipToPadding = false
                            bottomMargin = dip(16)
                        }

                        textInputLayout {
                            materialEditText {
                                id = R.id.username_edit_text
                                lparams(width = matchParent, height = wrapContent) {
                                    this.topMargin = dip(12)
                                }
                                hintResource = R.string.create_user_hint_username
                                textSize = 24f
                                setIconLeft(ContextCompat.getDrawable(ctx, R.mipmap.ic_username))
                                setFloatingLabel(MaterialEditText.FLOATING_LABEL_NORMAL)
                                setPrimaryColor(R.color.colorAccent)
                            }
                        }

                        textInputLayout {
                            materialEditText {
                                id = R.id.password_edit_text
                                lparams(width = matchParent, height = wrapContent)
                                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                                hintResource = R.string.create_user_hint_password
                                textSize = 24f
                                setIconLeft(ContextCompat.getDrawable(ctx, R.mipmap.ic_password))
                            }
                        }

                        // https://github.com/Kotlin/anko/issues/16
                        include<ActionProcessButton>(R.layout.process_button_login) {
                            onClick { handleOnLoginAction(ui) }
                        }

                        checkBox {
                            id = R.id.stay_logged_in_box
                            text = string(R.string.login_label_rememberMe)
                        }

                        textView {
                            id = R.id.error_text_view
                            textColor = ContextCompat.getColor(ctx, R.color.red_error)
                            text = string(R.string.error_view_login_text)
                            textSize = 14f
                            visibility = View.GONE
                        }.lparams {
                            gravity = Gravity.CENTER
                            topMargin = dip(8)
                            bottomMargin = dip(16)
                        }
                    }
                }
            }
        }
    }

    fun handleOnLoginAction(ui: AnkoContext<FragmentActivity>) {
        with(ui.owner) {
            val loginForm = find<LinearLayout>(R.id.login_form)
            val usernameTextView = find<MaterialEditText>(R.id.username_edit_text)
            val passwordTextView = find<MaterialEditText>(R.id.password_edit_text)
            val rememberMeBox = find<CheckBox>(R.id.stay_logged_in_box)

            val username = usernameTextView.text.toString()
            val password = passwordTextView.text.toString()

            loginForm.clearAnimation()

            if (username.isNullOrBlank()) {
                with(usernameTextView) {
                    clearAnimation()
                    startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.shake))
                }
                return
            }

            if (password.isNullOrBlank()) {
                with(passwordTextView) {
                    clearAnimation()
                    startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.shake))
                }
                return
            }

            if (!hideKeyboard(usernameTextView)) {
                hideKeyboard(passwordTextView)
            }

            presenter.doLogin(AuthCredentials(username, password, rememberMeBox.isChecked))
        }
    }

    override fun loginSuccess() {
        with(ankoView) {
            toast("Login Successfully!")
        }
    }

    override fun startMainActivity() {
        with(ankoView) {
            startActivity<MainActivity>()
            owner.finish()
        }
    }

    override fun showError() {
        with(ankoView) {
            toast("Error occurred")
        }
    }
}