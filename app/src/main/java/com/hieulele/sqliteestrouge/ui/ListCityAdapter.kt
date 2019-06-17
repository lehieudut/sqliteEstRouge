package com.hieulele.sqliteestrouge.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hieulele.sqliteestrouge.R
import com.hieulele.sqliteestrouge.base.BaseAdapter
import com.hieulele.sqliteestrouge.base.BaseLoadMoreAdapter
import com.hieulele.sqliteestrouge.model.City
import kotlinx.android.synthetic.main.item_list_city.view.*

class ListCityAdapter(context: Context, var mData: List<City>?, recyclerView: RecyclerView) :
    BaseLoadMoreAdapter<City>(context, mData, recyclerView) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.getContext()).inflate(R.layout.item_list_city, parent, false)
        return ListMovieHolder(view)
    }

    override fun onBindHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val homeHolder = holder as ListMovieHolder
        val data = mData?.get(position)
        homeHolder.mCityName.text = data?.city
        homeHolder.mCountryName.text = data?.country
        homeHolder.mPopulation.text = data?.population.toString()
    }

    override fun getItemType(position: Int): Int {
        return 1
    }

    class ListMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mCityName = itemView.mCityName
        val mCountryName = itemView.mCountryName
        val mPopulation = itemView.mPopulation
    }
}