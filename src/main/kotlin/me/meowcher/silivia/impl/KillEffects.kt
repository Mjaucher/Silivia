package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import me.meowcher.silivia.utils.world.USpawn
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.entity.EntityType
import net.minecraft.entity.LightningEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes

object KillEffects : Module(
    Casper.Reference.category,
    "kill-effects",
    "Shows Effects after Death."
), Melchior {

    private var group = settings.defaultGroup
    private var thunderGroup = settings.createGroup("Thunder Settings")
    private var soundGroup = settings.createGroup("Sound Settings")
    private var particleGroup = settings.createGroup("Particle Settings")

    private var range = group.add(IntSetting.Builder().name("range").defaultValue(15).sliderRange(0, 250).build())
    private var thunder = thunderGroup.add(BoolSetting.Builder().name("thunder-bolt").defaultValue(true).build())
    private var boltNumber = thunderGroup.add(IntSetting.Builder().name("bolt-number").defaultValue(1).sliderRange(1, 10).visible{thunder.get()}.build())
    private var sound = soundGroup.add(BoolSetting.Builder().name("sound").defaultValue(false).build())
    private var soundsNumber = soundGroup.add(IntSetting.Builder().name("sounds-number").defaultValue(1).sliderRange(1, 10).visible{sound.get()}.build())
    private var sounds = soundGroup.add(SoundEventListSetting.Builder().name("sounds").visible{sound.get()}.build())
    private var particle = particleGroup.add(BoolSetting.Builder().name("particle").defaultValue(true).build())
    private var particlesNumber = particleGroup.add(IntSetting.Builder().name("particles-number").defaultValue(1).sliderRange(1, 10).visible{particle.get()}.build())
    private var particles = particleGroup.add(ParticleTypeListSetting.Builder().name("particles").visible{particle.get()}.defaultValue(ParticleTypes.HEART).build())

    private var spawnEffectsEnd = false

    override fun onActivate() {
        spawnEffectsEnd = false
    }

    @EventHandler
    private fun onTickPostEvent(
        Event : Post
    ) {
        world.entities.forEach {

            if (it is PlayerEntity && player.distanceTo(it) < range.get()) {

                if (0 < it.health) spawnEffectsEnd = true

                if (spawnEffectsEnd && it.health <= 0F)
                    addEffects(it.x, it.y, it.z)
            }
        }
    }

    private fun addEffects(
        posX : Double,
        posY : Double,
        posZ : Double
    ) {
        spawnEffectsEnd = false

        if (thunder.get())
            IntRange(0, boltNumber.get()).forEach {
                USpawn.addEntity(LightningEntity(EntityType.LIGHTNING_BOLT, mc.world), posX, posY, posZ)
            }

        if (particle.get())
            IntRange(0, particlesNumber.get()).forEach {
                USpawn.addParticle(particles.get(), posX, posY + 1.75, posZ)
            }

        if (sound.get())
            IntRange(0, soundsNumber.get()).forEach {
                USpawn.playSound(sounds.get())
            }
    }
}
