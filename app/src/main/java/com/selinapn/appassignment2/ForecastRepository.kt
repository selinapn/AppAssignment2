package com.selinapn.appassignment2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> = _weeklyForecast

    // this will give the temperature values
    // map functions let you convert one type into another
    //it = each individual items
    fun loadForecast(zipcode: String){
        val randomValues = List(size=8) { Random.nextFloat() .rem(100) * 100}
        val forecastItems = randomValues.map{temp ->
            DailyForecast(temp, getTempDescription(temp))
        }
        _weeklyForecast.setValue(forecastItems)
    }

    private fun getTempDescription(temp: Float) : String {
        return when (temp) {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(60f) -> "Getting better"
            in 60f.rangeTo(80f) -> "This is perfect!"
            in 80f.rangeTo(90f) -> "This is getting too hot"
            in 90f.rangeTo(100f) -> "Where's the AC"
            else -> "Does not compute"
        }
    }
}
