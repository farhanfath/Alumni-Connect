package dts.pnj.farhan.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dts.pnj.farhan.data.database.alumni.Alumni
import dts.pnj.farhan.data.database.alumni.AlumniDao
import dts.pnj.farhan.data.database.user.User
import dts.pnj.farhan.data.database.user.UserDao

@Database(
    entities = [User::class, Alumni::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (Instance == null) {
                synchronized(AppDatabase::class.java) {
                    Instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_data").build()
                }
            }
            return Instance
        }
    }
    abstract fun userDao(): UserDao
    abstract fun alumniDao(): AlumniDao
}