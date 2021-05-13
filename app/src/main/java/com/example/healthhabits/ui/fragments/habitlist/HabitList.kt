package com.example.healthhabits.ui.fragments.habitlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthhabits.R
import com.example.healthhabits.data.models.Habit
import com.example.healthhabits.ui.fragments.habitlist.adapters.HabitListAdapter
import com.example.healthhabits.ui.viewmodels.HabitsViewModel
import kotlinx.android.synthetic.main.fragment_habit_list.*

class HabitList : Fragment(R.layout.fragment_habit_list) {

    private lateinit var habitList: List<Habit>
    private lateinit var habitViewModel: HabitsViewModel
    private lateinit var adapter: HabitListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = HabitListAdapter()
        rv_habits.adapter = adapter
        rv_habits.layoutManager = LinearLayoutManager(context)

        habitViewModel = ViewModelProvider(this).get(HabitsViewModel::class.java)

        habitViewModel.getAllHabits.observe(viewLifecycleOwner, Observer{
            adapter.setData(it)
            habitList = it

            if (it.isEmpty()){
                rv_habits.visibility = View.GONE
                tv_emptyView.visibility = View.VISIBLE
            } else{
                rv_habits.visibility = View.VISIBLE
                tv_emptyView.visibility = View.GONE
        }
        })

        setHasOptionsMenu(true)

        swipeToRefresh.setOnRefreshListener {
            adapter.setData(habitList)
            swipeToRefresh.isRefreshing = false
        }


        fab_add.setOnClickListener{
            findNavController().navigate(R.id.action_habitList_to_createHabitsItem)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_delete -> habitViewModel.deleteAllHabit()
        }

        return super.onOptionsItemSelected(item)
    }
}