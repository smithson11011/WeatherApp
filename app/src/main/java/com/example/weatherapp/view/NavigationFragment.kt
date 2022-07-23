package com.example.weatherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.adapters.NavigationViewAdapter
import com.example.weatherapp.databinding.FragmentNavigationBinding
import com.example.weatherapp.model.Weather
import com.example.weatherapp.viewmodel.AppState
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar

class NavigationFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle): NavigationFragment {
            val fragment = NavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentNavigationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel
    private val adapter = NavigationViewAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(BUNDLE_EXTRA, weather)
                manager.beginTransaction()
                    .add(R.id.activity_main_frame, WeatherView.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
        })
        private var isDataSetRus: Boolean = true


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = FragmentNavigationBinding.inflate(layoutInflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding.recyclerViewMenu.adapter = adapter
            binding.mainFragmentFAB.setOnClickListener { changeWeatherDataSet() }

            viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
            viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
            viewModel.getWeatherFromLocalSourceRus()
        }

        private fun changeWeatherDataSet() {
            if (isDataSetRus) {
                viewModel.getWeatherFromLocalSourceWorld()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_world)
            } else {
                viewModel.getWeatherFromLocalSourceRus()
                binding.mainFragmentFAB.setImageResource(R.drawable.ic_russia)
            }
            isDataSetRus = !isDataSetRus
        }

        private fun renderData(appState: AppState) {
            when (appState) {
                is AppState.Success -> {
                    binding.mainFragmentLoadingLayout.visibility = View.GONE
                    adapter.setWeather(appState.weatherData)
                }
                is AppState.Loading -> {
                    binding.mainFragmentLoadingLayout.visibility = View.VISIBLE
                }
                is AppState.Error -> {
                    binding.mainFragmentLoadingLayout.visibility = View.GONE
                    Snackbar
                        .make(
                            binding.mainFragmentFAB, getString(R.string.error),
                            Snackbar.LENGTH_INDEFINITE
                        )
                        .setAction(getString(R.string.reload)) {
                            viewModel.getWeatherFromLocalSourceRus()
                        }
                        .show()
                }
            }
        }

        interface OnItemViewClickListener {
            fun onItemViewClick(weather: Weather)
        }


    }