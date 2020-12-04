package com.example.weather.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.database.room_entities.LocationEntity

class WeatherFragmentStateAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    var locationList = listOf<LocationEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int  = locationList.size

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}