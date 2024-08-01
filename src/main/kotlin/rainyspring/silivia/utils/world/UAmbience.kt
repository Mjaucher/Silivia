package rainyspring.silivia.utils.world

import rainyspring.silivia.core.Melchior

object UAmbience: Melchior
{
    fun getTime(): Long = world.time

    fun setTime(
        time: Long
    ) {
        world.timeOfDay = time
    }

    fun setTime(
        time: Int
    ) {
        world.timeOfDay = time.toLong()
    }
}
