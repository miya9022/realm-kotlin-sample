package com.killer.sample.kotlin.realm.presenter

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.killer.sample.kotlin.realm.core.net.SetUpService
import com.killer.sample.kotlin.realm.core.session.ISessionManager
import com.killer.sample.kotlin.realm.model.realm.Repository
import com.killer.sample.kotlin.realm.model.realm.UserGithub
import com.killer.sample.kotlin.realm.util.getBase64String
import com.killer.sample.kotlin.realm.view.main.MainView
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryFirst
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by app on 7/5/17.
 */
class MainPresenter @Inject constructor(val service: SetUpService, val sessionManager: ISessionManager):
        MvpBasePresenter<MainView>() {
    private val TIME_LOAD_DELAY: Long = 128

    fun getDataUserExecution(userName: String) {
        getDataUser(userName).subscribe ({
            view.updateUserInfo(it)
        }, {
            error -> view.noticeError(error)
            getUserDataFromCacheExecution(userName)
        })
    }

    fun getUserDataFromCacheExecution(userName: String) {
        val task: Observable<UserGithub?>? = getUserDataFromCache(userName)
        if(task != null) {
            task.subscribe(UpdateUserDataFromCacheObserver())
        }
    }

    fun getListRepoExecution(userName: String) {
        getListRepo(userName).subscribe ({
            if (it.isNotEmpty()) updateReposObservable(it).subscribe(UpdateListRepoObserver())
        }, {
            error -> view.noticeError(error)
            getListRepoFromCacheExecution(userName)
        })
    }

    fun getListRepoFromCacheExecution(userName: String) {
        val task: Observable<Repository>? = getListRepoFromCache(userName)
        if (task != null) {
            task.subscribe({
                UpdateListRepoObserver()
            }, {
                error -> view.noticeError(error)
            })
        }
    }

    private fun getUserDataFromCache(userName: String): Observable<UserGithub?>? {
        var github = UserGithub().queryFirst { query -> query.equalTo("name", userName) }
        if(github == null) {
            github = UserGithub().queryFirst { query -> query.equalTo("loginName", userName) }
        }
        return when(github) {
            null -> null
            else -> Observable.just(github)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    private fun getListRepoFromCache(userName: String): Observable<Repository>? {
        var repos = Repository().query { query -> query.equalTo("owner.name", userName) }
        if (repos.isEmpty()) {
            repos = Repository().query { query -> query.equalTo("owner.loginName", userName) }
        }
        return when(repos.isEmpty()) {
            true -> null
            else -> updateReposObservable(repos)
        }
    }

    private fun getDataUser(userName: String): Observable<UserGithub> {
        return service.githubService.getGithubUser(sessionManager.defaultAuthorization(), userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getListRepo(userName: String): Observable<List<Repository>> {
        return service.githubService.getReposGithubUser(sessionManager.defaultAuthorization(), userName)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun updateReposObservable(iter: Iterable<Repository>) : Observable<Repository> {
        return Observable.zip<Repository, Long, Repository>(
                Observable.fromIterable(iter),
                Observable.interval(TIME_LOAD_DELAY, TimeUnit.MILLISECONDS),
                BiFunction { repo, timer -> repo })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    inner class UpdateUserDataFromCacheObserver: Observer<UserGithub?> {
        override fun onNext(t: UserGithub?) {
            if (t != null) view.updateUserInfo(t)
        }

        override fun onSubscribe(d: Disposable?) {
        }

        override fun onError(e: Throwable?) {
            if(e != null) view.noticeError(e)
        }

        override fun onComplete() {

        }
    }

    inner class UpdateListRepoObserver: Observer<Repository> {
        override fun onError(e: Throwable?) {
            if (e != null) view.noticeError(e)
        }

        override fun onSubscribe(d: Disposable?) {
        }

        override fun onNext(t: Repository?) {
            if (t != null) {
                view.insertRepo(t)
            }
        }

        override fun onComplete() {
            view.showTaskCompleted()
        }
    }
}