package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Melchior

object UAmbience : Melchior
{
    fun getTime() : Long = world.time

    fun setTime(
        Time : Long
    ) {
        world.timeOfDay = Time
    }

    fun setTime(
        Time : Int
    ) {
        world.timeOfDay = Time.toLong()
    }
}
