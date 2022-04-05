package me.meowcher.silivia.utils.world

import me.meowcher.silivia.utils.misc.UMinecraft.Companion.minecraft
import net.minecraft.util.math.BlockPos
import net.minecraft.block.Block

class UBlock
{
    companion object
    {
        fun getBlock(Position : BlockPos) : Block? {
            return minecraft.world!!.getBlockState(Position).block
        }
    }
}
