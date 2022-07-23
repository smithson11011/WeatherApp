package com.example.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentNavigationBinding
import com.example.weatherapp.databinding.FragmentWeatherViewBinding
import com.example.weatherapp.model.Weather

class WeatherView : Fragment() {

    private var _binding: FragmentWeatherViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
        if (weather != null) {
            val city = weather.city
            binding.weatherViewCityName.text = city.city
            binding.weatherViewLanLon.text =
                String.format(getString(R.string.city_name), city.lon, city.lat)
            binding.weatherViewTempText.text = weather.temperature.toString()
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): WeatherView {
            val fragment = WeatherView()
            fragment.arguments = bundle
            return fragment
        }
    }

}