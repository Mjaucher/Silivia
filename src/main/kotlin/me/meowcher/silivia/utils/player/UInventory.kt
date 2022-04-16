package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Global
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.screen.slot.SlotActionType

class UInventory
{
    companion object : Global
    {
        fun doUpdateSlot(slotNumber : Int)
        {
            UInteract.doPacketSend(UpdateSelectedSlotC2SPacket(slotNumber))
        }
        fun doSelectSlot(slotNumber : Int?)
        {
            inventory?.selectedSlot = slotNumber
        }
        fun getCurrentSlot() : Int
        {
            return inventory!!.selectedSlot
        }
        fun getItem(Slot : Int) : Item?
        {
            return if (Slot == -1) Items.AIR
            else inventory?.getStack(Slot)?.item
        }
        fun getItemSlot(Item : Item, Inventory : Boolean) : Int
        {
            val slots = if (Inventory) inventory?.size() else 8
            for (i in 0..slots!!) if (inventory?.getStack(i)?.item?.equals(Item) == true) return i
            return -1
        }
        fun doSlotMovement(Item : Item, moveSlot : Int)
        {
            val oldSlot = getItemSlot(Item, true)
            interaction?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, player)
            interaction?.clickSlot(0, moveSlot, 0, SlotActionType.PICKUP, player)
            interaction?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, player)
        }
    }
}
