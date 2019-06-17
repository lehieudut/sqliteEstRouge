package com.hieulele.sqliteestrouge.ui

import android.content.Context
import android.util.Log
import com.hieulele.sqliteestrouge.base.BasePresenter
import com.hieulele.sqliteestrouge.db.DatabaseHandler
import com.hieulele.sqliteestrouge.model.City

class ListCityPresenterImpl(context: Context) : BasePresenter<ListCityContract.ListCityView>(context),
    ListCityContract.ListCityPresenter {
    var mDbHandler: DatabaseHandler? = null

    init {
        mDbHandler = DatabaseHandler(context)
    }

    override fun getListCityDB(page: Int) {
        val listCity = mDbHandler!!.getListCity(page)
        Log.e("HIEU", "xxxx " + listCity?.size.toString())
        Log.e("HIEU", "xxxx111 " + mDbHandler!!.getTotalCities())
        if (listCity != null)
            view?.showListCity(listCity, page)
        else
            view?.errorQueryDB(page)
    }

    override fun closeDB() {
        mDbHandler?.close()
    }
}