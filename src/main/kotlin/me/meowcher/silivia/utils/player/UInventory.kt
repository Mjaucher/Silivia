package me.meowcher.silivia.utils.player

import me.meowcher.silivia.utils.misc.UMinecraft.Companion.minecraft
import net.minecraft.item.Item
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.screen.slot.SlotActionType

class UInventory
{
    companion object
    {
        fun doUpdateSlot(slotNumber : Int)
        {
            UInteract.doPacketSend(UpdateSelectedSlotC2SPacket(slotNumber))
        }
        fun doSelectSlot(slotNumber : Int)
        {
            minecraft.player?.inventory?.selectedSlot = slotNumber
        }
        fun getItemSlot(Item : Item?, Inventory : Boolean) : Int
        {
            val inv = if (Inventory) minecraft.player!!.inventory.size() else 9
            for (i in 0..inv) if (minecraft.player!!.inventory.getStack(i).item.equals(Item)) return i
            return -1
        }
        fun doSlotMovement(Item : Item?, moveSlot : Int)
        {
            val oldSlot = getItemSlot(Item, true)
            minecraft.interactionManager?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, minecraft.player)
            minecraft.interactionManager?.clickSlot(0, moveSlot, 0, SlotActionType.PICKUP, minecraft.player)
            minecraft.interactionManager?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, minecraft.player)
        }
    }
}
