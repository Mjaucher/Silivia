package me.meowcher.silivia.utils.world

import me.meowcher.silivia.core.Global
import net.minecraft.entity.Entity
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent

class USpawn
{
    companion object : Global
    {
        fun playSound(Sound : List<SoundEvent?>)
        {
            for (Event in Sound)
            {
                player?.playSound(Event, SoundCategory.MASTER, 1f, 1f)
            }
        }
        fun playSound(Sound : SoundEvent?)
        {
            player?.playSound(Sound, SoundCategory.MASTER, 1f, 1f)
        }
        fun addEntity(Entity : Entity, SpawnPosX : Double, SpawnPosY : Double, SpawnPosZ : Double)
        {
            Entity.updatePosition(SpawnPosX, SpawnPosY, SpawnPosZ)
            world?.addEntity(Entity.id, Entity)
        }
        fun addParticle(particleType : MutableList<ParticleType<*>>, posX : Double, posY : Double, posZ : Double)
        {
            for (ParticleType in particleType)
            {
                val type = ParticleType as ParticleEffect
                world?.addParticle(type, posX, posY, posZ, 0.0, 0.0, 0.0)
            }
        }
    }
}
