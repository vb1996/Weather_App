package com.assignment.temperature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assignment.temperature.R
import com.assignment.temperature.datamodel.ConsolidatedWeatherItem
import com.assignment.temperature.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content.view.*

class UpcomingDaysFragment : Fragment() {
    var mResponse: ConsolidatedWeatherItem? = null

    companion object {
        fun newInstance() = UpcomingDaysFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.days_fragment, container, false)
        setDataToView(mResponse)
        return mView
    }

    fun setDataToView(pResponse: ConsolidatedWeatherItem?) {
        when (pResponse != null) {
            true -> {
                mView.tv_date.text = pResponse?.applicableDate
                mView.tv_min_temp.text =
                    resources.getString(
                        R.string.min_temp_s,
                        pResponse?.minTemp?.toFloat()
                    )
                mView.tv_max_temp.text =
                    resources.getString(
                        R.string.max_temp_s,
                        pResponse?.maxTemp?.toFloat()
                    )
                mView.tv_acu_temp.text =
                    resources.getString(
                        R.string.accu_temp_s,
                        pResponse?.theTemp?.toFloat()
                    )
                mView.tv_hum_per.text = resources.getString(
                    R.string.hum_temp_s,
                    pResponse?.humidity?.toFloat()
                ).plus("%")
                mView.tv_air_per.text = resources.getString(
                    R.string.air_pres_s,
                    pResponse?.airPressure?.toFloat()
                )
                mView.tv_prob_per.text = resources.getString(
                    R.string.prob_temp_s,
                    pResponse?.predictability?.toFloat()
                ).plus("%")
                Glide
                    .with(context!!)
                    .load(getUrlBasedonState(pResponse?.weatherStateAbbr!!))
                    .centerCrop()
                    .into(mView.imageView);
                mView.tv_wind_speed.text = resources.getString(
                    R.string.wind_speed_s,
                    pResponse?.windSpeed?.toFloat()
                )
                mView.tv_wind_direc.text = resources.getString(
                    R.string.wind_speed_direc_s,
                    pResponse?.windDirection?.toFloat()
                )
                mView.tv_visibility.text = resources.getString(
                    R.string.visibility_direc_s,
                    pResponse?.visibility?.toFloat()
                ).plus("%")
            }
            false -> {
            }
        }
    }

    fun getUrlBasedonState(pState: String): String {
        return "https://www.metaweather.com/static/img/weather/png/64/" + pState + ".png"
    }

    fun setData(pConsolidatedWeatherItem: ConsolidatedWeatherItem) {
        this.mResponse = pConsolidatedWeatherItem
    }

}
