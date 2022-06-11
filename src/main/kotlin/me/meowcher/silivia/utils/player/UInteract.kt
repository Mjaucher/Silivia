package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Melchior
import net.minecraft.network.Packet
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d

object UInteract : Melchior {

    fun doPacketSend(
        Packet : Packet<*>
    ) = network.sendPacket(Packet)

    private fun doSwingHand(
        hand : Hand
    ) = player.swingHand(hand)

    fun doUseItemOnBlock(
        position : BlockPos,
        direction: Direction,
        hand : Hand
    ) {
        val result = BlockHitResult(Vec3d.of(position), direction, position, false)
        doPacketSend(PlayerInteractBlockC2SPacket(hand, result, 1))
        doSwingHand(hand)
    }
}
