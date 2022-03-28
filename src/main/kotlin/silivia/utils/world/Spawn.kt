package silivia.utils.world

import silivia.Silivia.Special.minecraft
import net.minecraft.entity.Entity
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent

class Spawn
{
    companion object
    {
        fun sound(Sound : List<SoundEvent?>)
        {
            for (Event in Sound)
            {
                minecraft.player?.playSound(Event, SoundCategory.MASTER, 1f, 1f)
            }
        }
        fun sound(Sound : SoundEvent?)
        {
            minecraft.player?.playSound(Sound, SoundCategory.MASTER, 1f, 1f)
        }
        fun entity(Entity : Entity, SpawnPosX : Double, SpawnPosY : Double, SpawnPosZ : Double)
        {
            Entity.updatePosition(SpawnPosX, SpawnPosY, SpawnPosZ)
            minecraft.world?.addEntity(Entity.id, Entity)
        }
        fun particle(particleType : MutableList<ParticleType<*>>, posX : Double, posY : Double, posZ : Double)
        {
            for (ParticleType in particleType)
            {
                val type = ParticleType as ParticleEffect
                minecraft.world?.addParticle(type, posX, posY, posZ, 0.0, 0.0, 0.0)
            }
        }
    }
}
