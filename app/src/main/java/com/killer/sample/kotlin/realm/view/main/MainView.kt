package com.killer.sample.kotlin.realm.view.main

import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.killer.sample.kotlin.realm.R
import com.killer.sample.kotlin.realm.core.view.ui.ClickListener
import com.killer.sample.kotlin.realm.core.view.ui.RecyclerItemTouchListener
import com.killer.sample.kotlin.realm.model.realm.Repository
import com.killer.sample.kotlin.realm.model.realm.UserGithub
import com.killer.sample.kotlin.realm.presenter.MainPresenter
import com.killer.sample.kotlin.realm.view.adapter.RepoAdapter
import com.vicpin.krealmextensions.save
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by app on 7/14/17.
 */
class MainView: AnkoComponent<MainActivity>, MainMvpView, ClickListener {
    lateinit var context: AnkoContext<MainActivity>
    private lateinit var repoAdapter: RepoAdapter
    lateinit var presenter: MainPresenter

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        context = ui
        repoAdapter = RepoAdapter()

        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER_HORIZONTAL
            padding = dip(16)

            editText {
                id = R.id.user_name
                hint = "Type user name"
                onTextChanged { retrieveData(it) }
            }.lparams(width = matchParent, height = wrapContent)

            linearLayout {
                id = R.id.main_content

                imageView {
                    id = R.id.avatar_image
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }.lparams(width = dip(80), height = dip(80))

                textView {
                    id = R.id.text_view_name
                    textSize = 20f
                }.lparams(width = wrapContent, height = wrapContent)

                textView {
                    id = R.id.text_view_public_repos
                    textSize = 20f
                }.lparams(width = wrapContent, height = wrapContent)

                recyclerView {
                    val orientation = LinearLayoutManager.VERTICAL
                    layoutManager = LinearLayoutManager(context.ctx, orientation, false)
                    id = R.id.list_repository
                    adapter = repoAdapter
                    addOnItemTouchListener(RecyclerItemTouchListener(this@MainView, this))
                }.lparams(width = matchParent, height = matchParent)

            }.apply {
                lparams(width = matchParent, height = wrapContent) {
                    orientation = LinearLayout.VERTICAL
                }
                gravity = Gravity.CENTER
            }

            frameLayout {
                id = R.id.error_view
                visibility = View.GONE

                textView {
                    id = R.id.error_message
                    textSize = 24f
                    gravity = Gravity.CENTER
                }.lparams(width = matchParent, height = wrapContent) {
                    gravity = Gravity.CENTER
                }

            }.apply {
                lparams(width = matchParent, height = matchParent)
                gravity = Gravity.CENTER
            }
        }
    }

    fun retrieveData(str: String) {
        Log.d("user typed", str)
        repoAdapter.reset()
        presenter.getDataUserExecution(str)
        presenter.getListRepoExecution(str)
    }

    override fun updateUserInfo(it: UserGithub?) {
        if (it == null) return

        it.save()
        val avatar = context.view.find<ImageView>(R.id.avatar_image)
        Glide.with(context.ctx)
                .load(it.avatarUrl)
                .fitCenter()
                .into(avatar)

        val tvName = context.view.find<TextView>(R.id.text_view_name)
        when (it.name) {
            null -> tvName.text = it.loginName
            else -> tvName.text = it.name
        }

        val tvPublicRepos = context.view.find<TextView>(R.id.text_view_public_repos)
        tvPublicRepos.text = "${it.publicRepos} repositories"
    }

    fun showContentView() {
        val contentView = context.view.find<LinearLayout>(R.id.main_content)
        val errorView = context.view.find<FrameLayout>(R.id.error_view)
        contentView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    fun showErrorMessage(message: String?) {
        val contentView = context.view.find<LinearLayout>(R.id.main_content)
        val errorView = context.view.find<FrameLayout>(R.id.error_view)
        contentView.visibility = View.GONE
        errorView.visibility = View.VISIBLE

        if (message != null) {
            val tvMessage = context.view.find<TextView>(R.id.error_message)
            tvMessage.text = message
        }
    }

    override fun insertRepo(repo: Repository) {
        repo.save()
        repoAdapter.insert(repo)
        showContentView()
    }

    override fun noticeError(error: Throwable) {
        Log.d("Error", error.message)
        showErrorMessage(error.message)
    }

    override fun showTaskCompleted() {
        with(context) {
            toast("task completed!")
        }
    }

    override fun onClick(view: View, position: Int) {
        val repo: Repository = repoAdapter.get(position)
        context.ctx.browse(repo.url!!)
    }

    override fun onLongClick(view: View, position: Int) {
        val repo: Repository = repoAdapter.get(position)
        context.ctx.toast("Repo info: " + repo.fullName)
    }

    protected fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChanged.invoke(p0.toString())
            }

            override fun afterTextChanged(editable: Editable?) {
            }
        })
    }
}