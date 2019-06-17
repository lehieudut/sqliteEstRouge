package com.hieulele.sqliteestrouge.ui

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hieulele.sqliteestrouge.R
import com.hieulele.sqliteestrouge.base.BaseActivity
import com.hieulele.sqliteestrouge.base.BaseLoadMoreAdapter
import com.hieulele.sqliteestrouge.model.City
import kotlinx.android.synthetic.main.activity_list_city.*

/**
 * Created by Le Hieu on 6/14/19.
 */

class ListCityActivity : BaseActivity<ListCityPresenterImpl>(), ListCityContract.ListCityView,
    BaseLoadMoreAdapter.OnLoadMoreListener {

    private val mListData = ArrayList<City>()
    private var mAdapter: ListCityAdapter? = null
    private var mPageLoadMore = 0

    override fun initPresenter() {
        mPresenter = ListCityPresenterImpl(this)
        mPresenter?.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_city)
        initUI()
        mPresenter?.getListCityDB(mPageLoadMore)
    }

    private fun initUI() {
        mToolBar.setTitleTextColor(Color.WHITE)
        mToolBar!!.title = "Utopia cities"
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mAdapter = ListCityAdapter(this, mListData, mRecyclerView)
        mAdapter!!.setOnLoadMoreListener(this)
        mRecyclerView!!.adapter = mAdapter
    }

    override fun onLoadMore() {
        mPageLoadMore++
        mPresenter?.getListCityDB(mPageLoadMore)
    }

    override fun showListCity(listMovie: ArrayList<City>, page: Int) {
        if (page == 0) {
            mListData.clear()
            mListData.addAll(listMovie)
            mAdapter?.notifyDataSetChanged()
        } else {
            if (!listMovie.isEmpty()) {
                Handler().postDelayed({ mAdapter?.notifyLoadMore(listMovie) }, 200)
            } else {
                Handler().postDelayed({
                    mAdapter?.dismissLoadMore()
                    mPageLoadMore--
                }, 200)
            }
        }
    }

    override fun errorQueryDB(page: Int) {
        if (page == 0) {
            mErrorMsgList?.visibility = View.VISIBLE
            mErrorMsgList?.text = resources.getString(R.string.error_query)
            mRecyclerView?.visibility = View.GONE
        } else {
            Handler().postDelayed({
                mAdapter?.dismissLoadMore()
                mPageLoadMore--
            }, 200)
        }
    }

    override fun onDestroy() {
        mPresenter?.closeDB()
        super.onDestroy()
    }
}