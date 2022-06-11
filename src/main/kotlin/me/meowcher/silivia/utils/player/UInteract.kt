package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Melchior
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
    companion object : Melchior
    {
        fun doPacketSend(Packet : Packet<*>)
        {
            network.sendPacket(Packet)
        }
        private fun doSwingHand(Hand : Hand)
        {
            player.swingHand(Hand)
        }
        fun doItemUse()
        {
            doPacketSend(PlayerInteractItemC2SPacket(Hand.MAIN_HAND, 1))
        }
        fun doDestroyBlock(destroyPosition : BlockPos, Action : DestroyBlock)
        {
            val action =
                if (Action == DestroyBlock.Abort) PlayerActionC2SPacket.Action.ABORT_DESTROY_BLOCK
                else PlayerActionC2SPacket.Action.START_DESTROY_BLOCK

            doPacketSend(PlayerActionC2SPacket(action, destroyPosition, Direction.UP))
        }
        fun doUseItemOnBlock(Position : BlockPos, Direction : Direction, Hand : Hand)
        {
            val result = BlockHitResult(Vec3d.of(Position), Direction, Position, false)
            doPacketSend(PlayerInteractBlockC2SPacket(Hand, result, 1))
            doSwingHand(Hand)
        }
        fun doAttack(Entity : Entity, Hand : Hand)
        {
            doPacketSend(PlayerInteractEntityC2SPacket.attack(Entity, player.isSneaking))
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

        enum class DestroyBlock
        {
            Start,
            Abort
        }
    }
}
