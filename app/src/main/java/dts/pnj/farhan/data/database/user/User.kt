package dts.pnj.farhan.data.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nim: String,
    val name: String,
    val className: String,
    val email: String,
    val password: String,
)