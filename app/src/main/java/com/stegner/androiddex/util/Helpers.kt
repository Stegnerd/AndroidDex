package com.stegner.androiddex.util

import com.stegner.androiddex.R

class Helpers {
    companion object {

        fun typeEnumFromId(id: Int) : TypeFilter{
            return when(id){
                R.id.type_filter_bug -> TypeFilter.Bug
                R.id.type_filter_dark -> TypeFilter.Dark
                R.id.type_filter_dragon -> TypeFilter.Dragon
                R.id.type_filter_electric -> TypeFilter.Electric
                R.id.type_filter_fairy -> TypeFilter.Fairy
                R.id.type_filter_fighting -> TypeFilter.Fighting
                R.id.type_filter_fire -> TypeFilter.Fire
                R.id.type_filter_flying -> TypeFilter.Flying
                R.id.type_filter_ghost -> TypeFilter.Ghost
                R.id.type_filter_ground -> TypeFilter.Ground
                R.id.type_filter_ice -> TypeFilter.Ice
                R.id.type_filter_normal -> TypeFilter.Normal
                R.id.type_filter_poison -> TypeFilter.Poison
                R.id.type_filter_psychic -> TypeFilter.Psychic
                R.id.type_filter_rock -> TypeFilter.Rock
                R.id.type_filter_steel -> TypeFilter.Steel
                R.id.type_filter_water -> TypeFilter.Water
                else -> TypeFilter.All
            }
        }
    }
}