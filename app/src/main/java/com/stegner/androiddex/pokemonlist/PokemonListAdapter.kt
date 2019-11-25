package com.stegner.androiddex.pokemonlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stegner.androiddex.data.pokemon.Pokemon
import com.stegner.androiddex.databinding.PokemonlistItemBinding
import com.stegner.androiddex.pokemonlist.PokemonListAdapter.ViewHolder

/**
 * Adapter for the pokemon list, has a reference to teh [PokemonListViewModel] to send actions back to it
 */
class PokemonListAdapter (private val viewModel: PokemonListViewModel) : ListAdapter<Pokemon, ViewHolder>(PokemonDiffCallback()){

    // Called on instantiation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    // called when binding to the ui
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // get the data of the item from the list
        val item = getItem(position)

        // attaches the data to the viewmodel and executes it
        holder.bind(viewModel, item)
    }

    class ViewHolder private constructor(val binding: PokemonlistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // this binds the viewmodel to the data
        fun bind(viewModel: PokemonListViewModel, item: Pokemon) {
            binding.viewModel = viewModel
            binding.pokemon = item
            binding.executePendingBindings()
        }

        // static object
        companion object {
            // viewholder is the container for the view
            fun from(parent: ViewGroup): ViewHolder {
                // get the layout
                val layoutInflater = LayoutInflater.from(parent.context)
                // instantiate it
                val binding = PokemonlistItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}


class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }

}