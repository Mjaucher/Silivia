package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Global

class UAmbience
{
    companion object : Global
    {
        fun getTime() : Long
        {
            return world!!.time
        }
        fun setTime(Time : Long)
        {
            world!!.timeOfDay = Time
        }
        fun setTime(Time : Int)
        {
            world!!.timeOfDay = Time.toLong()
        }
    }
}
