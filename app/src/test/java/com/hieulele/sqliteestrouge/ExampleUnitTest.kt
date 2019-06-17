package com.hieulele.sqliteestrouge

import android.os.Build.VERSION_CODES.LOLLIPOP
import com.hieulele.sqliteestrouge.db.DatabaseHandler
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    lateinit var dbHandler: DatabaseHandler

    @Before
    fun setUp() {
        dbHandler = DatabaseHandler(RuntimeEnvironment.application)
    }

    @Test
    fun getTotalRecord() {
        val expectTotal: Int = 272128 //Expect database count
        assertEquals(expectTotal, dbHandler.getTotalCities())
    }

    @After
    fun tearDown() {
        dbHandler.close()
    }
}
