package dts.pnj.farhan.data.repository

import android.annotation.SuppressLint
import android.content.Context
import dts.pnj.farhan.data.database.alumni.Alumni
import dts.pnj.farhan.data.database.alumni.AlumniDao
import dts.pnj.farhan.data.database.user.User
import dts.pnj.farhan.data.database.user.UserDao
import dts.pnj.farhan.data.response.UserPref
import kotlinx.coroutines.flow.Flow

class DataRepository (
    private val pref: UserPreference,
    private val context: Context,
    private val userDao: UserDao,
    private val alumniDao: AlumniDao
){

    // user preference

    suspend fun saveSession(user: UserPref) {
        pref.saveUser(user)
    }

    fun getUserData(): Flow<UserPref> {
        return pref.getUser()
    }

    suspend fun logout() {
        pref.logout()
    }

    // user database

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUser(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }

    // alumni database

    suspend fun insertAlumni(alumni: Alumni) {
        alumniDao.insertAlumni(alumni)
    }

    suspend fun checkAlumni(nim: String): Alumni? {
        return alumniDao.checkAlumni(nim)
    }

    suspend fun deleteAlumni(alumni: Alumni) {
        alumniDao.deleteAlumni(alumni)
    }

    suspend fun updateAlumni(alumni: Alumni) {
        alumniDao.updateAlumni(alumni)
    }

    suspend fun getAllAlumni(): List<Alumni> {
        return alumniDao.getAllAlumni()
    }

    suspend fun getAlumniById(alumniId: Int): Alumni? {
        return alumniDao.getAlumniById(alumniId)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DataRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            context: Context,
            userDao: UserDao,
            alumniDao: AlumniDao
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(userPreference, context, userDao, alumniDao)
            }.also { instance = it }
    }
}