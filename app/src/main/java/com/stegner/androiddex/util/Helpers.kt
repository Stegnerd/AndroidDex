package com.stegner.androiddex.util

import com.stegner.androiddex.R

class Helpers {
    companion object {

        fun typeEnumFromId(id: Int) : TypeFilter{
            return when(id){
                R.id.bug -> TypeFilter.Bug
                R.id.dark -> TypeFilter.Dark
                R.id.dragon -> TypeFilter.Dragon
                R.id.electric -> TypeFilter.Electric
                R.id.fairy -> TypeFilter.Fairy
                R.id.fighting -> TypeFilter.Fighting
                R.id.fire -> TypeFilter.Fire
                R.id.flying -> TypeFilter.Flying
                R.id.ghost -> TypeFilter.Ghost
                R.id.ground -> TypeFilter.Ground
                R.id.ice -> TypeFilter.Ice
                R.id.normal -> TypeFilter.Normal
                R.id.poison -> TypeFilter.Poison
                R.id.psychic -> TypeFilter.Psychic
                R.id.rock -> TypeFilter.Rock
                R.id.steel -> TypeFilter.Steel
                R.id.water -> TypeFilter.Water
                else -> TypeFilter.All
            }
        }
    }
}