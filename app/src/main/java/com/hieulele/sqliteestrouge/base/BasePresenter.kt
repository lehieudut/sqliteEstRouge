package com.hieulele.sqliteestrouge.base

import android.content.Context
import android.content.DialogInterface

/**
 * Created by Le Hieu on 6/14/19.
 *
 */

abstract class BasePresenter<T : BaseView>(var context: Context?) {

    protected val TAG = this.javaClass.simpleName
    var view: T? = null


    fun attachView(view: T) {
        this.view = view
    }

    protected fun detachView() {
        this.view = null
    }

}
