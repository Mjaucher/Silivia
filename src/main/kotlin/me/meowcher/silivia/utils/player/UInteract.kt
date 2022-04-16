package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Global
import net.minecraft.entity.Entity
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
        fun doSwingHand(Hand : Hand)
        {
            player?.swingHand(Hand)
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
        fun doUseItemOnBlock(Position : BlockPos, Direction : Direction, Hand : Hand)
        {
            val result = BlockHitResult(Vec3d.of(Position), Direction, Position, false)
            network?.sendPacket(PlayerInteractBlockC2SPacket(Hand, result))
            doSwingHand(Hand)
        }
        fun doAttack(Entity : Entity, Hand : Hand) {
            doPacketSend(PlayerInteractEntityC2SPacket.attack(Entity, player!!.isSneaking))
            doSwingHand(Hand)
            /*
            private fun doAttackMineCart()
            {
                for (mineCart in world!!.entities)
                {
                     if (player!!.distanceTo(mineCart) < attackRange.get())
                     {
                          if (mineCart is MinecartEntity)
                          {
                                UInteract.doAttack(mineCart, Hand.MAIN_HAND)
                          }
                     }
                 }
            }
            */
        }
    }
}
