package com.hieulele.sqliteestrouge.ui

import com.hieulele.sqliteestrouge.base.BaseView
import com.hieulele.sqliteestrouge.model.City

interface ListCityContract {
    /**
     * View - show view function
     */
    interface ListCityView : BaseView {
        fun showListCity(listMovie: ArrayList<City>, page: Int)

        fun errorQueryDB(page: Int)
    }

    /**
     * Presenter - logic and data function
     */
    interface ListCityPresenter {
        fun getListCityDB(page : Int)

        fun closeDB()
    }
}