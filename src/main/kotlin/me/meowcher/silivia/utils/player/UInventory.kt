package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.utils.misc.UMath
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.screen.slot.SlotActionType

class UInventory
{
    companion object : Melchior
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
        private fun clickSlot(Slot : Int, Key : MouseKey, actionType : SlotActionType)
        {
            val key = if (Key == MouseKey.Left) 0 else 1
            interaction?.clickSlot(0, Slot, key, actionType, player)
        }
        fun doDropSlot(Slot : Int)
        {
            clickSlot(Slot, MouseKey.Right, SlotActionType.THROW)
        }
        fun doDropItem(Item : Item)
        {
            clickSlot(getItemSlot(Item, true), MouseKey.Right, SlotActionType.THROW)
        }
        fun doSlotMovement(Item : Item, moveTo : Int)
        {
            val oldSlot = getItemSlot(Item, true)
            var action = 0

            do {
                action++
                if (action > 3) break
                val slot = if (!UMath.isEvenNumber(action)) oldSlot else moveTo
                clickSlot(slot, MouseKey.Left, SlotActionType.PICKUP)
            } while (true)
        }

        enum class MouseKey
        {
            Left,
            Right
        }
    }
}
