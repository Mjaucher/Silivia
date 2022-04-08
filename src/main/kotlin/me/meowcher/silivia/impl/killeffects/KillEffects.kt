package me.meowcher.silivia.impl.killeffects

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import me.meowcher.silivia.utils.world.USpawn
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.entity.EntityType
import net.minecraft.entity.LightningEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes

class KillEffects : Global, Module(Initializer.Category, "kill-effects", "Shows Effects after Death.")
{
    private var group = settings.defaultGroup
    private var thunderGroup = settings.createGroup("Thunder Settings")
    private var soundGroup = settings.createGroup("Sound Settings")
    private var particleGroup = settings.createGroup("Particle Settings")
    private var range = group.add(IntSetting.Builder().name("range").defaultValue(15).sliderRange(0, 250).build())
    private var thunder = thunderGroup.add(BoolSetting.Builder().name("thunder-bolt").defaultValue(true).build())
    private var sound = soundGroup.add(BoolSetting.Builder().name("sound").defaultValue(false).build())
    private var sounds = soundGroup.add(SoundEventListSetting.Builder().name("sounds").visible{sound.get()}.build())
    private var particle = particleGroup.add(BoolSetting.Builder().name("particle").defaultValue(true).build())
    private var particles = particleGroup.add(ParticleTypeListSetting.Builder().name("particles").visible{particle.get()}.defaultValue(ParticleTypes.HEART).build())
    private var spawnEffectsEnd = false

    override fun onActivate()
    {
        spawnEffectsEnd = false
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        for (entity in world!!.entities)
        {
            if (entity is PlayerEntity && player!!.distanceTo(entity) < range.get())
            {
                if (0 < entity.health) spawnEffectsEnd = true
                if (spawnEffectsEnd && entity.health <= 0F) addEffects(entity.x, entity.y, entity.z)
            }
        }
    }

    private fun addEffects(X : Double, Y : Double, Z : Double) {
        if (thunder.get()) USpawn.addEntity(LightningEntity(EntityType.LIGHTNING_BOLT, mc.world), X, Y, Z)
        if (particle.get()) USpawn.addParticle(particles.get(), X, Y + 1.75, Z)
        if (sound.get()) USpawn.playSound(sounds.get())
        spawnEffectsEnd = false
    }
}
