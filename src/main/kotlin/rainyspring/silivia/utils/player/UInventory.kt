package rainyspring.silivia.utils.player

import rainyspring.silivia.core.Melchior
import net.minecraft.item.*

object UInventory: Melchior {

    fun doSelectSlot(
        slot: Int
    ) {
        inventory.selectedSlot = slot
    }

    fun getCurrentSlot(): Int = inventory.selectedSlot

    fun getItem(
        slot: Int
    ): Item =
        if (slot == -1) Items.AIR
        else inventory.getStack(slot).item

    fun getItemSlot(
        item: Item,
        inv: Boolean
    ) : Int {

        val slots =
            if (inv) inventory.size()
            else 9

        IntRange(0, slots).forEach {
            if (inventory.getStack(it)?.item?.equals(item) == true)
                return it
        }

        return -1
    }
}
