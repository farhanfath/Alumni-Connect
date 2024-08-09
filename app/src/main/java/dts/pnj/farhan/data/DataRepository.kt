package dts.pnj.farhan.data

import android.annotation.SuppressLint
import android.content.Context
import dts.pnj.farhan.data.database.user.User
import dts.pnj.farhan.data.database.user.UserDao
import dts.pnj.farhan.data.response.UserPref
import kotlinx.coroutines.flow.Flow

class DataRepository (
    private val pref: UserPreference,
    private val context: Context,
    private val userDao: UserDao
){

    suspend fun saveSession(user: UserPref) {
        pref.saveUser(user)
    }

    fun getUserData(): Flow<UserPref> {
        return pref.getUser()
    }

    suspend fun logout() {
        pref.logout()
    }

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUser(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            context: Context,
            userDao: UserDao
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(userPreference, context, userDao)
            }.also { instance = it }
    }
}