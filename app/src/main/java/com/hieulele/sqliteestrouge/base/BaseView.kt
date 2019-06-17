package com.hieulele.sqliteestrouge.base

import android.content.DialogInterface

/**
 * Created by Le Hieu on 6/14/19.
 *
 */

interface BaseView {

    fun showLoading()

    fun dismissLoading()

    fun showError(message: String?)
}
