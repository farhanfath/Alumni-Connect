package dts.pnj.farhan.utils

import android.content.Context
import dts.pnj.farhan.data.repository.DataRepository
import dts.pnj.farhan.data.repository.UserPreference
import dts.pnj.farhan.data.repository.dataStore
import dts.pnj.farhan.data.database.AppDatabase

object Utility {
    fun provideRepository(context: Context): DataRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val userDao = requireNotNull(AppDatabase.getDatabase(context)?.userDao()) {
            "Failed to obtain UserDao"
        }
        val alumniDao = requireNotNull(AppDatabase.getDatabase(context)?.alumniDao()) {
            "Failed to obtain alumniDao"
        }
        return DataRepository.getInstance(pref, context, userDao, alumniDao)
    }
}