package dts.pnj.farhan.utils

import android.content.Context
import dts.pnj.farhan.data.DataRepository
import dts.pnj.farhan.data.UserPreference
import dts.pnj.farhan.data.dataStore
import dts.pnj.farhan.data.database.AppDatabase

object Utility {
    fun provideRepository(context: Context): DataRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val profileDao = requireNotNull(AppDatabase.getDatabase(context)?.userDao()) {
            "Failed to obtain UserDao"
        }
        return DataRepository.getInstance(pref, context, profileDao)
    }
}