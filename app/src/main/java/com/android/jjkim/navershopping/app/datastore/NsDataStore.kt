package com.android.jjkim.navershopping.app.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.android.jjkim.navershopping.app.NSApp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class NsDataStore {
    companion object {
        const val DEFAULT_COUNT = 0
    }

    private object PreperencesKeys {
        val SEARCH_COUNTER = intPreferencesKey("key_search_counter")
    }

    val searchCounter: Flow<Int> = NSApp.getDataStore().data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            preference[PreperencesKeys.SEARCH_COUNTER] ?: 0 }

    suspend fun incrementSearchCounter() {
        NSApp.getDataStore().edit { settings ->
            val currentCounterValue = settings[PreperencesKeys.SEARCH_COUNTER] ?: 0
            settings[PreperencesKeys.SEARCH_COUNTER] = currentCounterValue + 1
        }
    }
}