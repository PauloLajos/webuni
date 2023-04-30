package practice.exampleroomdatabase.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExampleDao {
    @Query("SELECT * FROM ExampleEntity")
    fun getAllExamples(): List<ExampleEntity>

    @Query("SELECT * FROM ExampleEntity WHERE token = :token")
    fun getSomeExamples(token: String): List<ExampleEntity>

    @Insert
    fun insertGrades(vararg examples: ExampleEntity)

    @Delete
    fun deleteGrade(example: ExampleEntity)
}