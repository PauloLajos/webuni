package practice.exampleroomdatabase.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true) var Id: Long?,
    //@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "token") var token: String,
    @ColumnInfo(name = "designer") var designer: String? = null,
    @ColumnInfo(name = "desc") var desc: String? = null
)
