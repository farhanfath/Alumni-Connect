package dts.pnj.farhan.data.database.alumni

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alumni")
data class Alumni(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nim: String,
    val name: String,
    val birthPlace: String,
    val birthDate: String,
    val address: String,
    val religion: String,
    val phoneNumber: String,
    val joinYear: String,
    val graduateYear: String,
    val job: String,
    val position: String
)