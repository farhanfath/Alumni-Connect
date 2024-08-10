package dts.pnj.farhan.data.database.alumni

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AlumniDao {
    @Insert
    suspend fun insertAlumni(alumni: Alumni)

    @Delete
    suspend fun deleteAlumni(alumni: Alumni)

    @Update
    suspend fun updateAlumni(alumni: Alumni)

    @Query("SELECT * FROM alumni WHERE nim = :nim LIMIT 1")
    suspend fun checkAlumni(nim: String): Alumni?

    @Query("SELECT * FROM alumni")
    suspend fun getAllAlumni(): List<Alumni>
}