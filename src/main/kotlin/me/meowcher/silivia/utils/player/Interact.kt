package me.meowcher.silivia.utils.player

import net.minecraft.client.MinecraftClient
import net.minecraft.item.Item
import net.minecraft.network.Packet
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.network.packet.s2c.play.*
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d

class Interact
{
    companion object
    {
        fun packetSend(Packet : Packet<*>)
        {
            MinecraftClient.getInstance().networkHandler?.sendPacket(Packet)
        }
        fun onDisconnect(Packet : Packet<*>)
        {
            MinecraftClient.getInstance().player?.networkHandler?.onDisconnect(Packet as DisconnectS2CPacket?)
        }
        private fun swing(Hand : Hand)
        {
            MinecraftClient.getInstance().player?.swingHand(Hand)
        }
        fun use()
        {
            packetSend(PlayerInteractItemC2SPacket(Hand.MAIN_HAND))
        }
        private fun startDestroyBlock(destroyPosition : BlockPos?)
        {
            packetSend(PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, destroyPosition, Direction.UP))
        }
        fun abortDestroyBlock(destroyPosition : BlockPos)
        {
            packetSend(PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, destroyPosition, Direction.UP))
        }
        fun place(Item : Item?, Position : BlockPos?, Direction : Direction, slotBack : Boolean)
        {
            var boo = true
            val oldSlot = MinecraftClient.getInstance().player?.inventory?.selectedSlot
            val result = BlockHitResult(Vec3d.of(Position), Direction, Position, false)
            if (MinecraftClient.getInstance().world?.getBlockState(Position)?.isAir == false) return
            val slot = Inventory.Slot.Get.itemSlot(Item, false)
            if (slot != oldSlot)
            {
                Inventory.Slot.selectSlot(slot)
                boo = false
            }
            MinecraftClient.getInstance().interactionManager
                ?.interactBlock(
                    MinecraftClient.getInstance().player,
                    MinecraftClient.getInstance().world,
                    Hand.MAIN_HAND,
                    result
                )
            if (!boo && slotBack) MinecraftClient.getInstance().player?.inventory?.selectedSlot = oldSlot
            startDestroyBlock(Position)
            swing(Hand.MAIN_HAND)
        }
    }
}
