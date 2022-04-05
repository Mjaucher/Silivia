package me.meowcher.silivia.utils.player

import me.meowcher.silivia.utils.misc.UMinecraft.Companion.minecraft
import net.minecraft.item.Item
import net.minecraft.network.Packet
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d

class UInteract
{
    companion object
    {
        fun doPacketSend(Packet : Packet<*>)
        {
            minecraft.networkHandler?.sendPacket(Packet)
        }
        private fun doSwingHand()
        {
            minecraft.player?.swingHand(Hand.MAIN_HAND)
        }
        fun doUse()
        {
            doPacketSend(PlayerInteractItemC2SPacket(Hand.MAIN_HAND))
        }
        private fun doStartDestroyBlock(destroyPosition : BlockPos?)
        {
            doPacketSend(PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, destroyPosition, Direction.UP))
        }
        fun doAbortDestroyBlock(destroyPosition : BlockPos)
        {
            doPacketSend(PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, destroyPosition, Direction.UP))
        }
        fun doPlace(Item : Item, Position : BlockPos?, Direction : Direction, slotBack : Boolean)
        {
            var boo = true
            val oldSlot = minecraft.player?.inventory?.selectedSlot
            val result = BlockHitResult(Vec3d.of(Position), Direction, Position, false)
            if (minecraft.world?.getBlockState(Position)?.isAir == false) return
            val slot = UInventory.getItemSlot(Item, false)
            if (slot != oldSlot)
            {
                UInventory.doSelectSlot(slot)
                boo = false
            }
            minecraft.interactionManager?.interactBlock(minecraft.player, minecraft.world, Hand.MAIN_HAND, result)
            if (!boo && slotBack) minecraft.player?.inventory?.selectedSlot = oldSlot
            doStartDestroyBlock(Position)
            doSwingHand()
        }
    }
}
