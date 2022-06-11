package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Melchior
import net.minecraft.entity.Entity
import net.minecraft.particle.*
import net.minecraft.sound.*

object USpawn : Melchior
{
    private fun playSound(sound : SoundEvent) =
        player.playSound(sound, SoundCategory.MASTER, 1F, 1F)

    fun playSound(sounds : List<SoundEvent>) =
        sounds.forEach {
            playSound(it)
        }

    fun addEntity(
        entity : Entity,
        posX : Double,
        posY : Double,
        posZ : Double
    ) {
        entity.updatePosition(posX, posY, posZ)
        world.addEntity(
            entity.id,
            entity
        )
    }

    fun addParticle(
        particleType : MutableList<ParticleType<*>>,
        posX : Double,
        posY : Double,
        posZ : Double
    ) = particleType.forEach {
        val type = it as ParticleEffect
        world.addParticle(type, posX, posY, posZ, 0.0, 0.0, 0.0)
    }
}
