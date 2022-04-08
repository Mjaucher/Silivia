package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Global
import net.minecraft.item.Item
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
        fun getSlot() : Int?
        {
            return inventory?.selectedSlot
        }
        fun getItemSlot(Item : Item?, Inventory : Boolean) : Int
        {
            val inv = if (Inventory) player!!.inventory.size() else 9
            for (i in 0..inv) if (inventory?.getStack(i)?.item?.equals(Item) == true) return i
            return -1
        }
        fun doSlotMovement(Item : Item?, moveSlot : Int)
        {
            val oldSlot = getItemSlot(Item, true)
            interaction?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, player)
            interaction?.clickSlot(0, moveSlot, 0, SlotActionType.PICKUP, player)
            interaction?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, player)
        }
    }
}
