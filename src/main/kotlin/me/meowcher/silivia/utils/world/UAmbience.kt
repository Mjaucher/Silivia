package me.meowcher.silivia.utils.world

import me.meowcher.silivia.utils.misc.UMinecraft.Companion.minecraft

class UAmbience
{
    companion object
    {
        fun setTime(Time : Long)
        {
            minecraft.world!!.timeOfDay = Time
        }
        fun setTime(Time : Int)
        {
            minecraft.world!!.timeOfDay = Time.toLong()
        }
    }
}
