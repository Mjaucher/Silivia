package rainyspring.silivia.utils.world

import rainyspring.silivia.core.Melchior
import meteordevelopment.meteorclient.systems.friends.Friends
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.Vec2f

object UEntity : Melchior
{
    fun getTarget(
        range : Int,
        ignoreFriends : Boolean
    ) : PlayerEntity? {

        world.entities.forEach {
            if (player.distanceTo(it) <= range
                && it is PlayerEntity
                && it != player
                && Friends.get().isFriend(it) != ignoreFriends
            ) return it
        }
        return null
    }

    fun isSurrounded(
        entity : PlayerEntity
    ) : Boolean {

        var ind = 0

        arrayOf(
            Vec2f(0F, 1F),
            Vec2f(0F, -1F),
            Vec2f(1F, 0F),
            Vec2f(-1F, 0F)
        ).forEach {
            if (!world.getBlockState(entity.blockPos.add(
                    it.x.toInt(), 0, it.y.toInt()
                )).isAir) ind++
        }

        return ind == 4
    }
}
