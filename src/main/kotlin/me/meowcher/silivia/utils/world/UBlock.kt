package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Global
import net.minecraft.util.math.BlockPos
import net.minecraft.block.Block

class UBlock
{
    companion object : Global
    {
        fun getBlock(Position : BlockPos) : Block? {
            return world!!.getBlockState(Position).block
        }
        fun isAir(Position : BlockPos) : Boolean {
            return world?.getBlockState(Position)?.isAir == true
        }
    }
}
