package com.example.healthhabits.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthhabits.data.models.Habit
import com.example.healthhabits.logic.dao.HabitDao

@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitDataBase : RoomDatabase() {

    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDataBase? = null

        fun getDataBase(context: Context): HabitDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, HabitDataBase::class.java, "habit_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}