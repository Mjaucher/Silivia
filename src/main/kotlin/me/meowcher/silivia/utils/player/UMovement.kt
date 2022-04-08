package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Global
import net.minecraft.util.math.MathHelper

class UMovement
{
    companion object : Global
    {
        fun doCenterTp()
        {
            player!!.setPosition((MathHelper.floor(player!!.x)) + 0.5, player!!.y, (MathHelper.floor(player!!.z)) + 0.5)
        }
    }
}
