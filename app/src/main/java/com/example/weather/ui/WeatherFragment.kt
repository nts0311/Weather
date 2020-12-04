package com.example.weather.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.viewmodels.WeatherFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_weather.*
import java.text.SimpleDateFormat
import java.util.*


private const val ARG_LOCATION_ID = "locationId"


@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var locationId: Long? = null

    private lateinit var viewModel : WeatherFragViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            locationId = it.getLong(ARG_LOCATION_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(activity!!)
            .get("weather_frag_loc_$locationId", WeatherFragViewModel::class.java)

        viewModel.locationId = locationId!!

        txt.text = locationId.toString()
        registerObservers()
    }

    private fun registerObservers()
    {
        viewModel.getWeatherInfo.observe(viewLifecycleOwner)
        {
            if (it == null) return@observe
            val yourmilliseconds = System.currentTimeMillis()
            val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm:ss")
            val resultdate = Date(yourmilliseconds)
            Toast.makeText(context,sdf.format(resultdate), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(locationId: Long) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_LOCATION_ID, locationId)
                }
            }
    }
}