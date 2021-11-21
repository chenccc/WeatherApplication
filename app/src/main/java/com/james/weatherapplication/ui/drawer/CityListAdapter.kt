package com.james.weatherapplication.ui.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.james.weatherapplication.data.model.CityWeather
import com.james.weatherapplication.databinding.CityItemBinding

class CityListAdapter(private val viewModel: DrawerViewModel): ListAdapter<CityWeather, CityListAdapter.ViewHolder>(CALLBACK) {
    companion object {
        private val CALLBACK = object: DiffUtil.ItemCallback<CityWeather>() {
            override fun areItemsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CityWeather, newItem: CityWeather): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(val binding: CityItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CityWeather) {
            binding.item = data
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CityItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
