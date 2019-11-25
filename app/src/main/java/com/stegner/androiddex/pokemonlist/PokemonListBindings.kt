package com.stegner.androiddex.pokemonlist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stegner.androiddex.data.pokemon.Pokemon

/**
 * [BindingAdapter] for the [Pokemon] list
 *
 * Sets what items will be in the list when applying it in the layout
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Pokemon>){
    (listView.adapter as PokemonListAdapter).submitList(items)
}