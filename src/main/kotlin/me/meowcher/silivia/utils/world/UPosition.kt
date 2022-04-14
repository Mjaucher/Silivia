package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Global
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos

class UPosition
{
    companion object : Global
    {
        fun getEntity(entity : Entity) : BlockPos
        {
            return BlockPos(entity.blockX, entity.blockY, entity.blockZ)
        }
    }
}
