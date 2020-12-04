package com.example.weather.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.weather.database.room_entities.LocationEntity
import com.example.weather.ui.WeatherFragment

class WeatherFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    var locationList = listOf<LocationEntity>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = locationList.size

    override fun createFragment(position: Int): Fragment =
        WeatherFragment.newInstance(locationList[position].dbId)
}