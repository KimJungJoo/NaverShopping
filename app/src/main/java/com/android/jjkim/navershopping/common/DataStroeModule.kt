package com.android.jjkim.navershopping.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStroeModule (private val context: Context) {
    private val Context.nsDataStore: DataStore<Preferences> by preferencesDataStore(name = "datastore")
    private val loginCounterKey = intPreferencesKey("key_login_counter")

    val loginCounter: Flow<Int> = context.nsDataStore.data.map { preference ->
        preference[loginCounterKey] ?: 0 }

    suspend fun incrementLoginCounter() {
        context.nsDataStore.edit { settings ->
            val currentCounterValue = settings[loginCounterKey] ?: 0
            settings[loginCounterKey] = currentCounterValue + 1
        }
    }
}