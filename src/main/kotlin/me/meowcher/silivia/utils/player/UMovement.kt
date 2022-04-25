package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Melchior
import net.minecraft.util.math.MathHelper

class UMovement
{
    companion object : Melchior
    {
        fun doCenterTp()
        {
            fun floor(Position : Double) : Double
            {
                return (MathHelper.floor(Position)) + 0.5
            }

            player!!.setPosition(floor(player!!.x), player!!.y, floor(player!!.z))
        }
    }
}
