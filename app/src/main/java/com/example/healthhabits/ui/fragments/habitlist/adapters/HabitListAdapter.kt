package com.example.healthhabits.ui.fragments.habitlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.healthhabits.R
import com.example.healthhabits.data.models.Habit
import com.example.healthhabits.ui.fragments.habitlist.HabitListDirections
import com.example.healthhabits.utils.Calculations
import kotlinx.android.synthetic.main.recycler_habit_item.view.*

class HabitListAdapter: RecyclerView.Adapter<HabitListAdapter.MyViewHolder>(){

    var habitList = emptyList<Habit>()
    val TAG = "HabitListAdapter"

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init{
            itemView.cv_cardView.setOnClickListener{
                val position= adapterPosition
                Log.d(TAG, "Item clicked at: $position")
                Log.d(TAG, "ID: ${habitList[position].id}")

                val action = HabitListDirections.actionHabitListToUpdateHabitsItem(habitList[position])
                itemView.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitListAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_habit_item, parent, false))
    }

    override fun onBindViewHolder(holder: HabitListAdapter.MyViewHolder, position: Int) {
        val currentHabit = habitList[position]
        holder.itemView.iv_habit_icon.setImageResource(currentHabit.imageId)
        holder.itemView.tv_item_title.text = currentHabit.habitTitle
        holder.itemView.tv_item_description.text = currentHabit.habitDesc
        holder.itemView.tv_timeElapsed.text = Calculations.calculateTimeBetweenDates(currentHabit.habitStartTime)
        holder.itemView.tv_item_createdTimeStamp.text = "Since ${currentHabit.habitStartTime}"
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun setData(habit: List<Habit>){
        this.habitList = habit
        notifyDataSetChanged()
    }

}