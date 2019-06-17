package com.hieulele.sqliteestrouge.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.hieulele.sqliteestrouge.R


/**
 * Created by Le Hieu on 6/14/19.
 *
 */
abstract class BaseActivity<T : BasePresenter<*>> : AppCompatActivity(), BaseView {

    protected var TAG = this.javaClass.simpleName
    protected var mPresenter: T? = null
    protected abstract fun initPresenter()
    protected var mProgressDialog: Dialog? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setSystemBarTheme(this, true)
        }
        initPresenter()
    }

    protected fun showAlertDialog(msg: String?) {
        AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, null)
                .show()
    }

    override fun showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = Dialog(this)
            val inflate = LayoutInflater.from(this).inflate(R.layout.progress_dialog, null)
            mProgressDialog?.setContentView(inflate)
            mProgressDialog?.setCancelable(false)
            mProgressDialog?.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT))
        }
        mProgressDialog?.show()
    }

    override fun dismissLoading() {
        if (mProgressDialog != null && mProgressDialog?.isShowing!!) {
            mProgressDialog?.dismiss()
        }
    }

    override fun showError(message: String?) {
        showAlertDialog(message)
    }

    /** Changes the System Bar Theme.  */
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun setSystemBarTheme(pActivity: Activity, pIsDark: Boolean) {
        // Fetch the current flags.
        val lFlags = pActivity.window.decorView.systemUiVisibility
        // Update the SystemUiVisibility dependening on whether we want a Light or Dark theme.
        pActivity.window.decorView.systemUiVisibility =
            if (pIsDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
