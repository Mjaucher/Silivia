package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Global
import meteordevelopment.meteorclient.systems.friends.Friends
import net.minecraft.entity.player.PlayerEntity


class UEntity
{
    companion object : Global
    {
        fun getTarget(Range : Int, ignoreFriends : Boolean) : PlayerEntity?
        {
            for (target in world!!.entities)
            {
                if (player!!.distanceTo(target) <= Range && target is PlayerEntity && target != player)
                {
                    if (Friends.get().isFriend(target) != ignoreFriends)
                    {
                        return target
                    }
                }
            }
            return null
        }

        fun isSurrounded(entity : PlayerEntity) : Boolean {
            val blockPos = entity.blockPos
            return world?.getBlockState(blockPos.add(0, 0, 1))?.isAir == false && world?.getBlockState(blockPos.add(0, 0, -1))?.isAir == false &&
                world?.getBlockState(blockPos.add(1, 0, 0))?.isAir == false && world?.getBlockState(blockPos.add(-1, 0, 0))?.isAir  == false
        }
    }
}
