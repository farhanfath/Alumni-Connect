package dts.pnj.farhan.data.database.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dts.pnj.farhan.data.response.UserPref

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUser(username: String, password: String): User?
}