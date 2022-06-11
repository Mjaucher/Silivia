package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Melchior
import net.minecraft.item.*

object UInventory : Melchior {

    fun doSelectSlot(
        slotNumber : Int
    ) {
        inventory.selectedSlot = slotNumber
    }

    fun getCurrentSlot() : Int = inventory.selectedSlot

    fun getItem(
        slot : Int
    ) : Item =
        if (slot == -1) Items.AIR
        else inventory.getStack(slot).item

    fun getItemSlot(
        Item : Item,
        Inventory : Boolean
    ) : Int {

        val slots =
            if (Inventory) inventory.size()
            else 9

        IntRange(0, slots).forEach {
            if (inventory.getStack(it)?.item?.equals(Item) == true)
                return it
        }

        return -1
    }
}
