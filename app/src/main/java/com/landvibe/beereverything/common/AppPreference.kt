package com.landvibe.beereverything.common

import android.content.SharedPreferences

object AppPreference {
    private const val PREFS_FILENAME = "beerdot.pref"
    private const val LAST_UPDATE_TIME = "last_update_time"
    private val preference: SharedPreferences = App.instance.getSharedPreferences(PREFS_FILENAME, 0)

    var lastUpdateTime: Long
        get() = preference.getLong(LAST_UPDATE_TIME, -1)
        set(value) = preference.edit().putLong(LAST_UPDATE_TIME, value).apply()
}