package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Melchior
import net.minecraft.util.math.BlockPos
import net.minecraft.block.Block

object UBlock : Melchior {

    fun getBlock(
        position : BlockPos
    ) : Block = world.getBlockState(position).block

    fun isAir(Position : BlockPos) : Boolean = world.getBlockState(Position).isAir
}
