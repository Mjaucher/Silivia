package me.meowcher.silivia.impl.nointeract

import me.meowcher.silivia.utils.addon.Initializer
import meteordevelopment.meteorclient.events.packets.PacketEvent.Send
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.block.AnvilBlock
import net.minecraft.block.Blocks
import net.minecraft.block.ShulkerBoxBlock
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket
import net.minecraft.util.math.BlockPos

class NoInteract : Module(Initializer.Category, "no-interact", "Clicks disabler.")
{
    private var miscellaneousGroup = settings.createGroup("Miscellaneous Settings")
    private var containersGroup = settings.createGroup("Containers Settings")
    private var miscellaneous = miscellaneousGroup.add(BoolSetting.Builder().name("miscellaneous").defaultValue(true).build())
    private var anvil = miscellaneousGroup.add(BoolSetting.Builder().name("anvil").visible{miscellaneous.get()}.defaultValue(true).build())
    private var beacon = miscellaneousGroup.add(BoolSetting.Builder().name("beacon").visible{miscellaneous.get()}.defaultValue(false).build())
    private var stoneCutter = miscellaneousGroup.add(BoolSetting.Builder().name("stone-cutter").visible{miscellaneous.get()}.defaultValue(false).build())
    private var grindstone = miscellaneousGroup.add(BoolSetting.Builder().name("grindstone").visible{miscellaneous.get()}.defaultValue(false).build())
    private var loom = miscellaneousGroup.add(BoolSetting.Builder().name("loom").visible{miscellaneous.get()}.defaultValue(false).build())
    private var brewingStand = miscellaneousGroup.add(BoolSetting.Builder().name("brewing-stand").visible{miscellaneous.get()}.defaultValue(false).build())
    private var craftingTable = miscellaneousGroup.add(BoolSetting.Builder().name("crafting-table").visible{miscellaneous.get()}.defaultValue(false).build())
    private var enchantingTable = miscellaneousGroup.add(BoolSetting.Builder().name("enchanting-table").visible{miscellaneous.get()}.defaultValue(false).build())
    private var cartographyTable = miscellaneousGroup.add(BoolSetting.Builder().name("cartography-table").visible{miscellaneous.get()}.defaultValue(false).build())
    private var smithingTable = miscellaneousGroup.add(BoolSetting.Builder().name("smithing-table").visible{miscellaneous.get()}.defaultValue(false).build())
    private var containers = containersGroup.add(BoolSetting.Builder().name("containers").defaultValue(false).build())
    private var shulkerBox = containersGroup.add(BoolSetting.Builder().name("shulker-box").visible{containers.get()}.defaultValue(false).build())
    private var trappedChest = containersGroup.add(BoolSetting.Builder().name("trapped-chest").visible{containers.get()}.defaultValue(false).build())
    private var enderChest = containersGroup.add(BoolSetting.Builder().name("ender-chest").visible{containers.get()}.defaultValue(false).build())
    private var chest = containersGroup.add(BoolSetting.Builder().name("chest").visible{containers.get()}.defaultValue(false).build())
    private var furnace = containersGroup.add(BoolSetting.Builder().name("furnace").visible{containers.get()}.defaultValue(false).build())
    private var barrel = containersGroup.add(BoolSetting.Builder().name("barrel").visible{containers.get()}.defaultValue(false).build())
    private var dispenser = containersGroup.add(BoolSetting.Builder().name("dispenser").visible{containers.get()}.defaultValue(false).build())
    private var dropper = containersGroup.add(BoolSetting.Builder().name("dropper").visible{containers.get()}.defaultValue(false).build())
    private var hopper = containersGroup.add(BoolSetting.Builder().name("hopper").visible{containers.get()}.defaultValue(false).build())
    private var blastFurnace = containersGroup.add(BoolSetting.Builder().name("blast-furnace").visible{containers.get()}.defaultValue(false).build())
    private var smoker = containersGroup.add(BoolSetting.Builder().name("smoker").visible{containers.get()}.defaultValue(false).build())

    private fun openableBlocks(BlockPos : BlockPos) : Boolean
    {
        val getBlock = mc.world!!.getBlockState(BlockPos).block
        return (getBlock is AnvilBlock && anvil.get() || getBlock === Blocks.BEACON && beacon.get()
            || getBlock === Blocks.STONECUTTER && stoneCutter.get() || getBlock === Blocks.GRINDSTONE && grindstone.get()
            || getBlock === Blocks.LOOM && loom.get() || getBlock === Blocks.BREWING_STAND && brewingStand.get()
            || getBlock === Blocks.CRAFTING_TABLE && craftingTable.get() || getBlock === Blocks.ENCHANTING_TABLE && enchantingTable.get()
            || getBlock === Blocks.CARTOGRAPHY_TABLE && cartographyTable.get() || getBlock === Blocks.SMITHING_TABLE && smithingTable.get()) && miscellaneous.get()
            || (getBlock is ShulkerBoxBlock && shulkerBox.get() || getBlock === Blocks.CHEST && chest.get()
            || getBlock === Blocks.TRAPPED_CHEST && trappedChest.get() || getBlock === Blocks.ENDER_CHEST && enderChest.get()
            || getBlock === Blocks.FURNACE && furnace.get() || getBlock === Blocks.BARREL && barrel.get()
            || getBlock === Blocks.DISPENSER && dispenser.get() || getBlock === Blocks.DROPPER && dropper.get()
            || getBlock === Blocks.HOPPER && hopper.get() || getBlock === Blocks.BLAST_FURNACE && blastFurnace.get()
            || getBlock === Blocks.SMOKER && smoker.get()) && containers.get()
    }

    @EventHandler private fun onEventA(Packet : Send)
    {
        if (Packet.packet !is PlayerInteractBlockC2SPacket) return
        if (openableBlocks((Packet.packet as PlayerInteractBlockC2SPacket).blockHitResult.blockPos))
        {
            Packet.cancel()
        }
    }
}
