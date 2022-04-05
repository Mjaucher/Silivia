package me.meowcher.silivia.utils.world

import me.meowcher.silivia.utils.misc.UMinecraft.Companion.minecraft
import net.minecraft.entity.player.PlayerEntity

class UEntity
{
    companion object
    {
        fun getTarget(Range : Int) : PlayerEntity?
        {
            for (target in minecraft.world!!.entities)
            {
                if (minecraft.player!!.distanceTo(target) <= Range)
                {
                    if (target is PlayerEntity && target !== minecraft.player)
                    {
                        return target
                    }
                }
            }
            return null
        }
    }
}
