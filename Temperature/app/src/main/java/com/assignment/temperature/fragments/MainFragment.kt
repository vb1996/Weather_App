package com.assignment.temperature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.assignment.temperature.MainActivity
import com.assignment.temperature.R
import com.assignment.temperature.datamodel.ConsolidatedWeatherItem
import com.assignment.temperature.datamodel.Response
import com.assignment.temperature.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getLiveDataResponse().observe(requireActivity(), Observer {
            setDataToView(it)
        })
    }

    override fun onResume() {
        viewModel.downloadWeatherInfo()
        super.onResume()
    }

    fun setDataToView(pResponse: Response) {
        when (!pResponse.consolidatedWeather.isNullOrEmpty()) {
            true -> {
                tv_date.text = pResponse.consolidatedWeather?.get(0)?.applicableDate
                tv_min_temp.text =
                    resources.getString(
                        R.string.min_temp_s,
                        pResponse.consolidatedWeather?.get(0)?.minTemp?.toFloat()
                    )
                tv_max_temp.text =
                    resources.getString(
                        R.string.max_temp_s,
                        pResponse.consolidatedWeather?.get(0)?.maxTemp?.toFloat()
                    )
                tv_acu_temp.text =
                    resources.getString(
                        R.string.accu_temp_s,
                        pResponse.consolidatedWeather?.get(0)?.theTemp?.toFloat()
                    )
                tv_hum_per.text = resources.getString(
                    R.string.hum_temp_s,
                    pResponse.consolidatedWeather?.get(0)?.humidity?.toFloat()
                ).plus("%")
                tv_air_per.text = resources.getString(
                    R.string.air_pres_s,
                    pResponse.consolidatedWeather?.get(0)?.airPressure?.toFloat()
                )
                tv_prob_per.text = resources.getString(
                    R.string.prob_temp_s,
                    pResponse.consolidatedWeather?.get(0)?.predictability?.toFloat()
                ).plus("%")

                tv_wind_speed.text = resources.getString(
                    R.string.wind_speed_s,
                    pResponse.consolidatedWeather?.get(0)?.windSpeed?.toFloat()
                )
                tv_wind_direc.text = resources.getString(
                    R.string.wind_speed_direc_s,
                    pResponse.consolidatedWeather?.get(0)?.windDirection?.toFloat()
                )
                tv_visibility.text = resources.getString(
                    R.string.visibility_direc_s,
                    pResponse.consolidatedWeather?.get(0)?.visibility?.toFloat()
                ).plus("%")
                Glide
                    .with(context!!)
                    .load(getUrlBasedonState(pResponse.consolidatedWeather?.get(0)?.weatherStateAbbr!!))
                    .centerCrop()
                    .into(imageView);

                l_d1.setOnClickListener { openUpcoming(pResponse.consolidatedWeather?.get(1)!!) }
                l_d2.setOnClickListener { openUpcoming(pResponse.consolidatedWeather?.get(2)!!) }
                l_d3.setOnClickListener { openUpcoming(pResponse.consolidatedWeather?.get(3)!!) }
                l_d4.setOnClickListener { openUpcoming(pResponse.consolidatedWeather?.get(4)!!) }
                l_d5.setOnClickListener { openUpcoming(pResponse.consolidatedWeather?.get(5)!!) }
            }
            false -> {
            }
        }
    }

    fun openUpcoming(pConsolidatedWeatherItem: ConsolidatedWeatherItem) {
        (requireActivity() as MainActivity).openUpcomingDays(pConsolidatedWeatherItem)
    }

    fun getUrlBasedonState(pState: String): String {
        return "https://www.metaweather.com/static/img/weather/png/64/" + pState + ".png"
    }
}
