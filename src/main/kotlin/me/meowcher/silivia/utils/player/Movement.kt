package me.meowcher.silivia.utils.player

import me.meowcher.silivia.utils.misc.Minecraft.Companion.minecraft
import net.minecraft.util.math.MathHelper

class Movement
{
    companion object
    {
        fun centerTP()
        {
            minecraft.player!!.setPosition(
                (MathHelper.floor(minecraft.player!!.x)) + 0.5,
                minecraft.player!!.y,
                (MathHelper.floor(minecraft.player!!.z)) + 0.5
            )
        }
    }
}
