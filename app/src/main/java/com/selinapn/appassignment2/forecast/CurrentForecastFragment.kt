package com.selinapn.appassignment2.forecast


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.selinapn.appassignment2.*
import com.selinapn.appassignment2.api.CurrentWeather
import com.selinapn.appassignment2.api.DailyForecast

/**
 * A simple [Fragment] subclass.
 */
class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)
        val locationName: TextView = view.findViewById(R.id.locationName)
        val tempText: TextView = view.findViewById(R.id.tempText)
        val emptyText = view.findViewById<TextView>(R.id.emptyText)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())



      val currentWeatherObserver = Observer<CurrentWeather> { weather ->
          emptyText.visibility = View.GONE
          locationName.visibility = View.VISIBLE
          tempText.visibility = View.VISIBLE
          progressBar.visibility = View.GONE

          locationName.text = weather.name
          tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
      }

        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when (savedLocation) {
                is Location.Zipcode -> {
                    progressBar.visibility = View.VISIBLE
                    forecastRepository.loadCurrentForecast(savedLocation.zipcode)
                }
            }

        }

        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)


        return view
    }


    private fun showLocationEntry() {
        val action= CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }




    }
