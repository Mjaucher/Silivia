package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.utils.world.UBlock
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
    companion object : Global
    {
        fun doPacketSend(Packet : Packet<*>)
        {
            network?.sendPacket(Packet)
        }
        fun doSwingHand()
        {
            player?.swingHand(Hand.MAIN_HAND)
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
        fun doInteractBlock(Position : BlockPos, Direction : Direction, Hand : Hand)
        {
            val result = BlockHitResult(Vec3d.of(Position), Direction, Position, false)
            network?.sendPacket(PlayerInteractBlockC2SPacket(Hand, result))
        }
    }
}
