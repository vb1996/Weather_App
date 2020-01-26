package com.assignment.temperature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.assignment.temperature.datamodel.ConsolidatedWeatherItem
import com.assignment.temperature.fragments.MainFragment
import com.assignment.temperature.fragments.UpcomingDaysFragment


class MainActivity : AppCompatActivity() {

    var mDefultFragment = MainFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            getDefaultFragment()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun getDefaultFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mDefultFragment)
            .addToBackStack(mDefultFragment::class.java.simpleName)
            .commit()
    }

    fun openUpcomingDays(pConsolidatedWeatherItem: ConsolidatedWeatherItem) {
        var mFragment = UpcomingDaysFragment.newInstance()
        mFragment.setData(pConsolidatedWeatherItem)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, mFragment)
            .addToBackStack(UpcomingDaysFragment::class.java.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        val fragmentManager = this@MainActivity.supportFragmentManager
        val myFragment: Fragment =
            fragmentManager.getFragments().get(fragmentManager.getFragments().size - 1)
        when (myFragment) {
            is UpcomingDaysFragment -> {
                super.onBackPressed()
                return
            }
            else -> {
                finish()
            }
        }
    }
}
