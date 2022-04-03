package me.meowcher.silivia.utils.world

import net.minecraft.block.*
import net.minecraft.block.Block

class Blocks
{
    companion object
    {
        fun openableBlocks(Block : Block) : Boolean
        {
            return Block is TrappedChestBlock || Block is DispenserBlock || Block is ShulkerBoxBlock
                || Block is SmokerBlock || Block is HopperBlock || Block is BlastFurnaceBlock
                || Block is DropperBlock || Block is CraftingTableBlock || Block is SmithingTableBlock
                || Block is CartographyTableBlock || Block is BarrelBlock || Block is EnchantingTableBlock
                || Block is LoomBlock || Block is BrewingStandBlock || Block is GrindstoneBlock
                || Block is StonecutterBlock || Block is BeaconBlock || Block is ChestBlock
                || Block is EnderChestBlock || Block is AnvilBlock
        }
    }
}
