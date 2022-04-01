package me.meowcher.silivia.utils.player

import net.minecraft.client.MinecraftClient
import net.minecraft.item.Item
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.screen.slot.SlotActionType

class Inventory
{
    object Slot
    {
        private val minecraft = MinecraftClient.getInstance()

        fun updateSlot(slotNumber : Int)
        {
            Interact.packetSend(UpdateSelectedSlotC2SPacket(slotNumber))
        }
        fun selectSlot(slotNumber : Int)
        {
            minecraft.player?.inventory?.selectedSlot = slotNumber
        }
        fun move(Item : Item?, moveSlot : Int)
        {
            val oldSlot = Get.itemSlot(Item, true)
            minecraft.interactionManager?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, minecraft.player)
            minecraft.interactionManager?.clickSlot(0, moveSlot, 0, SlotActionType.PICKUP, minecraft.player)
            minecraft.interactionManager?.clickSlot(0, oldSlot, 0, SlotActionType.PICKUP, minecraft.player)
        }
        object Get
        {
            fun itemSlot(Item : Item?, Inventory : Boolean) : Int
            {
                val inv = if (Inventory) minecraft.player!!.inventory.size() else 9
                for (i in 0..inv) if (minecraft.player!!.inventory.getStack(i).item.equals(Item)) return i
                return -1
            }
        }
    }
}
