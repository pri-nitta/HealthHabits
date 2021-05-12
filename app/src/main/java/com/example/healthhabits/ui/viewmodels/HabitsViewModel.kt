package com.example.healthhabits.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.healthhabits.data.database.HabitDataBase
import com.example.healthhabits.data.models.Habit
import com.example.healthhabits.logic.repository.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsViewModel(application: Application): AndroidViewModel(application){
    private val repository: HabitRepository
    val getAllHabits: LiveData<List<Habit>>

    init{
        val habitDao = HabitDataBase.getDataBase(application).habitDao()
        repository = HabitRepository(habitDao)
        getAllHabits = repository.getAllHabits
    }

    fun addHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHabit(habit)
        }
    }

    fun updateHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateHabit(habit)
        }
    }


    fun deleteAllHabit(habit: Habit){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllHabit()
        }
    }

}