package com.example.healthhabits.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.healthhabits.R
import com.example.healthhabits.data.models.Habit
import com.example.healthhabits.ui.viewmodels.HabitsViewModel
import com.example.healthhabits.utils.Calculations
import kotlinx.android.synthetic.main.fragment_create_habits_item.*
import java.util.*

class CreateHabitsItem : Fragment(R.layout.fragment_create_habits_item),
    TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var habitViewModel: HabitsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habitViewModel = ViewModelProvider(this).get(HabitsViewModel::class.java)
        btn_confirm.setOnClickListener {
            //addHabitToDB()
        }

        pickDateAndTime()

        drawableSelected()

    }

    private fun addHabitToDB(){
        title = et_habitTitle.text.toString()
        description = et_habitDescription.text.toString()
        timeStamp = "$cleanDate $cleanTime"

        if (!(title.isEmpty() || description.isEmpty() || timeStamp.isEmpty() || drawableSelected == 0 )){
            val habit = Habit(0, title, description, timeStamp, drawableSelected)

            habitViewModel.addHabit(habit)
            Toast.makeText(context, "Habit added successfully", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_createHabitsItem_to_habitList)
        }else{
            Toast.makeText(context, "Please find all the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawableSelected() {
        iv_fastFoodSelected.setOnClickListener {
            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_baseline_fastfood_24

            iv_smokingSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }

        iv_smokingSelected.setOnClickListener {
            iv_smokingSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_baseline_smoke_free_24

            iv_fastFoodSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }

        iv_teaSelected.setOnClickListener {
            iv_teaSelected.isSelected = !iv_teaSelected.isSelected
            drawableSelected = R.drawable.ic_baseline_emoji_food_beverage_24

            iv_fastFoodSelected.isSelected = false
            iv_smokingSelected.isSelected = false
        }

    }

    private fun pickDateAndTime() {
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minuteX: Int) {
        cleanTime = Calculations.cleanTime(hourOfDay, minuteX)
        tv_timeSelected.text = "Time: $cleanTime"
    }

    override fun onDateSet(view: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "Date: $cleanDate"
    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        // be careful that the calendar starts with 0
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

}