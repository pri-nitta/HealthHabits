package com.example.healthhabits.logic.repository

import androidx.lifecycle.LiveData
import com.example.healthhabits.data.models.Habit
import com.example.healthhabits.logic.dao.HabitDao

class HabitRepository(private val habitDao: HabitDao) {
    val getAllHabits: LiveData<List<Habit>> = habitDao.getAllHabits()

    suspend fun addHabit(habit:Habit){
        habitDao.addHabit(habit)
    }

    suspend fun updateHabit(habit:Habit){
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit:Habit){
        habitDao.deleteHabit(habit)
    }

    suspend fun deleteAllHabit(){
        habitDao.deleteAll()
    }
}