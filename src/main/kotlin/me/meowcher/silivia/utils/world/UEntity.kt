package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Global
import net.minecraft.entity.player.PlayerEntity

class UEntity
{
    companion object : Global
    {
        fun getTarget(Range : Int) : PlayerEntity?
        {
            for (target in world!!.entities)
            {
                if (player!!.distanceTo(target) <= Range)
                {
                    if (target is PlayerEntity && target !== player)
                    {
                        return target
                    }
                }
            }
            return null
        }
    }
}
