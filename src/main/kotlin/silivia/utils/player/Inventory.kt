package silivia.utils.player

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import silivia.Silivia.Special.minecraft

class Inventory
{
    object Get
    {
        fun itemCount(Item : Item) : Int
        {
            var count = 0
            for (i in 0..35)
            {
                val itemStack : ItemStack? = minecraft.player?.inventory?.getStack(i)
                if (itemStack != null)
                {
                    if (itemStack.item == Item)
                    {
                        count += itemStack.count
                    }
                }
            }
            return count
        }
    }
}
