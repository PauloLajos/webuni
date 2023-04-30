package practice.exampleroomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(ExampleEntity::class), version = 1)
abstract class ExampleDatabase : RoomDatabase() {

    abstract fun exampleDao(): ExampleDao

    companion object {
        private var INSTANCE: ExampleDatabase? = null

        fun getInstance(context: Context): ExampleDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ExampleDatabase::class.java, "grade.db")
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}