package com.freshlybakedandroid.weathercompose.utill

object TemperatureUnitManager {
    private var currentUnit: TemperatureUnit = TemperatureUnit.CELSIUS

    fun setUnit(unit: TemperatureUnit) {
        currentUnit = unit
    }

    fun getUnit(): TemperatureUnit {
        return currentUnit
    }
}

enum class TemperatureUnit(val unit: String) {
    KELVIN("default"),
    CELSIUS("metric"),
    FAHRENHEIT("imperial")
}