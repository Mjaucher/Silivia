package rainyspring.silivia.utils.world

import rainyspring.silivia.core.Melchior
import net.minecraft.entity.Entity
import net.minecraft.particle.*
import net.minecraft.sound.*

object USpawn : Melchior
{
    private fun playSound(sound: SoundEvent) =
        player.playSound(sound, 1F, 1F)

    fun playSound(sounds: List<SoundEvent>) =
        sounds.forEach {
            playSound(it)
        }

    fun addEntity(
        entity: Entity,
        posX: Double,
        posY: Double,
        posZ: Double
    ) {
        entity.updatePosition(posX, posY, posZ)
        world.addEntity(entity)
    }

    fun addParticle(
        particleType: MutableList<ParticleType<*>>,
        posX: Double,
        posY: Double,
        posZ: Double
    ) = particleType.forEach {
        val type = it as ParticleEffect
        world.addParticle(type, posX, posY, posZ, 0.0, 0.0, 0.0)
    }
}
