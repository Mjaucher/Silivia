package me.meowcher.silivia.utils.world

import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos

class Position
{
    object Get
    {
        fun entity(entity : Entity) : BlockPos
        {
            return BlockPos(entity.blockX, entity.blockY, entity.blockZ)
        }
    }
}
