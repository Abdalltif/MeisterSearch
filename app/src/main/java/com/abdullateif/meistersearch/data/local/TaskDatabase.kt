package com.abdullateif.meistersearch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdullateif.meistersearch.common.Config
import com.abdullateif.meistersearch.domain.entity.TaskDetails

@Database(entities = [TaskDetails::class], version = Config.DB_VERSION, exportSchema = true)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null )
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    Config.DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}