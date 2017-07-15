package com.killer.sample.kotlin.realm.core.anko

import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.resources

/**
 * Created by app on 7/8/17.
 */
inline fun AnkoContext<*>.string(id: Int): String = resources.getString(id)