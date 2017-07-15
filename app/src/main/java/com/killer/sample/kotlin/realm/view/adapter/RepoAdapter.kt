package com.killer.sample.kotlin.realm.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.killer.sample.kotlin.realm.R
import com.killer.sample.kotlin.realm.model.realm.Repository
import org.jetbrains.anko.*

/**
 * Created by app on 7/5/17.
 */
class RepoItemView: AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        frameLayout {
            lparams(width = matchParent, height = wrapContent)
            padding = dip(8)

            textView {
                id = R.id.repo_name
                textSize = 20f
            }.lparams(width = wrapContent, height = matchParent) {
                gravity = Gravity.CENTER_VERTICAL or Gravity.START
            }

            textView {
                id = R.id.repo_size
                textSize = 20f
            }.lparams(width = wrapContent, height = matchParent) {
                gravity = Gravity.CENTER_VERTICAL or Gravity.END
            }
        }
    }
}

class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val repoName: TextView = itemView.find(R.id.repo_name)
    val repoSize: TextView = itemView.find(R.id.repo_size)

    fun bind(repo: Repository) {
        repoName.text = repo.name
        repoSize.text = repo.size.toString()
    }
}

class RepoAdapter(val repoList: ArrayList<Repository> = ArrayList<Repository>()) : RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
        Holder(RepoItemView().createView(AnkoContext.Companion.create(parent!!.context, parent)))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(get(position))

    override fun getItemCount() = repoList.size

    fun get(position: Int) = repoList[position]

    fun insert(repo: Repository) {
        repoList.add(repo)
        notifyItemInserted(repoList.size-1)
    }

    fun remove(position: Int) {
        repoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun reset() {
        if (repoList.isNotEmpty()) {
            repoList.clear()
            notifyDataSetChanged()
        }
    }
}