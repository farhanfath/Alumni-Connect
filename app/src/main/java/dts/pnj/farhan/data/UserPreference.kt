package dts.pnj.farhan.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dts.pnj.farhan.data.response.UserPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveUser(user: UserPref) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[NIM_KEY] = user.nim
            preferences[NAME_KEY] = user.name
            preferences[CLASS_KEY] = user.className
        }
    }

    fun getUser(): Flow<UserPref> {
        return dataStore.data.map { preferences ->
            UserPref(
                email = preferences[EMAIL_KEY] ?: "",
                nim = preferences[NIM_KEY] ?: "",
                name = preferences[NAME_KEY] ?: "",
                className = preferences[CLASS_KEY] ?: ""
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
            Log.d("Logout", "User logged out and preferences cleared.")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val NIM_KEY = stringPreferencesKey("nim")
        private val NAME_KEY = stringPreferencesKey("name")
        private val CLASS_KEY = stringPreferencesKey("class_name")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}