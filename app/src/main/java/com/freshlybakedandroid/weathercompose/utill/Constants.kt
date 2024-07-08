package com.freshlybakedandroid.weathercompose.utill


class Constants {

    /*Temperature is available in Fahrenheit, Celsius and Kelvin units.
    For temperature in Fahrenheit use units=imperial
    For temperature in Celsius use units=metric
    Temperature in Kelvin is used by default, no need to use units parameter in API call*/

    companion object {

        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        const val TEMP_UNIT_IMPERIAL =  "imperial" //Fahrenheit
        const val TEMP_UNIT_METRIC = "metric" //Celsius

        const val UNIT_CELSIUS_SYMBOL = "\u2103"
        const val UNIT_FAHRENHEIT_SYMBOL = "\u2109"

        const val SPEED_UNIT_METRIC = "m/s"
        const val SPEED_UNIT_IMPERIAL = "miles/h"

//        var CURRENTLAT = "19.0760"
//        var CURRENTLONG = "72.8777"

    }

}