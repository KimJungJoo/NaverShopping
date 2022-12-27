package com.android.jjkim.navershopping.common.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
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
        val AGREE_NECESSARY_PERMISSION = booleanPreferencesKey("key_agree_necessary_permission")
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

    suspend fun setAgreeNecessaryPermission(value: Boolean) {
        NSApp.getDataStore().edit { settings ->
            settings[PreperencesKeys.AGREE_NECESSARY_PERMISSION] = value
        }
    }

    val isAgreeNecessaryPermission: Flow<Boolean> = NSApp.getDataStore().data
        .catch { exception ->
            if(exception is IOException)
                emit(emptyPreferences())
            else
                throw exception
        }
        .map { preference ->
            preference[PreperencesKeys.AGREE_NECESSARY_PERMISSION] ?: false
        }
}