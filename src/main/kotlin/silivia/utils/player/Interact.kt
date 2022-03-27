package silivia.utils.player

import net.minecraft.network.Packet
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.network.packet.s2c.play.*
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import silivia.Silivia.Special.minecraft

class Interact
{
    companion object
    {
        fun packetSend(Packet : Packet<*>)
        {
            minecraft.networkHandler?.sendPacket(Packet)
        }
        fun onDisconnect(Packet : Packet<*>)
        {
            minecraft.player?.networkHandler?.onDisconnect(Packet as DisconnectS2CPacket?)
        }
        fun swing(Hand : Hand)
        {
            minecraft.player?.swingHand(Hand)
        }
        fun swap(SlotNumber : Int)
        {
            packetSend(UpdateSelectedSlotC2SPacket(SlotNumber))
        }
        fun use()
        {
            packetSend(PlayerInteractItemC2SPacket(Hand.MAIN_HAND))
        }
        fun startDestroyBlock(BlockPos : BlockPos)
        {
            packetSend(PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, BlockPos, Direction.UP))
        }
        fun abortDestroyBlock(BlockPos : BlockPos)
        {
            packetSend(PlayerActionC2SPacket(PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK, BlockPos, Direction.UP))
        }
    }
    object Place
    {
        fun packet(BlockHitResult : BlockHitResult?, Hand : Hand)
        {
            packetSend(PlayerInteractBlockC2SPacket(Hand, BlockHitResult))
            swing(Hand)
        }
        fun normal(BlockHitResult : BlockHitResult?, Hand : Hand)
        {
            minecraft.interactionManager?.interactBlock(minecraft.player, minecraft.world, Hand, BlockHitResult)
            swing(Hand)
        }
    }
}
