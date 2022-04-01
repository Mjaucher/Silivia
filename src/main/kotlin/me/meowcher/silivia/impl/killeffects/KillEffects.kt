package me.meowcher.silivia.impl.killeffects

import me.meowcher.silivia.utils.addon.Initializer
import me.meowcher.silivia.utils.world.Spawn
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.entity.EntityType
import net.minecraft.entity.LightningEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes

class KillEffects : Module(Initializer.Category, "kill-effects", "Shows Effects after Death.")
{
    private var group = settings.defaultGroup
    private var thunderGroup = settings.createGroup("Thunder Settings")
    private var soundGroup = settings.createGroup("Sound Settings")
    private var particleGroup = settings.createGroup("Particle Settings")
    private var range = group.add(IntSetting.Builder().name("range").defaultValue(15).sliderRange(0, 250).build())
    private var thunder = thunderGroup.add(BoolSetting.Builder().name("Thunder-Bolt").defaultValue(true).build())
    private var numberBolts = thunderGroup.add(IntSetting.Builder().name("number-bolts").defaultValue(1).sliderRange(1, 10).visible{thunder.get()}.build())
    private var sound = soundGroup.add(BoolSetting.Builder().name("sound").defaultValue(false).build())
    private var sounds = soundGroup.add(SoundEventListSetting.Builder().name("sounds").visible{sound.get()}.build())
    private var particle = particleGroup.add(BoolSetting.Builder().name("particle").defaultValue(true).build())
    private var particles = particleGroup.add(ParticleTypeListSetting.Builder().name("particles").visible{particle.get()}.defaultValue(ParticleTypes.HEART).build())
    private var numA = 0
    private var numB = 0

    override fun onActivate()
    {
        numA = 1
    }
    @EventHandler private fun onEventA(Event : Post)
    {
        val lightning = LightningEntity(EntityType.LIGHTNING_BOLT, mc.world)
        for (entity in mc.world!!.entities)
        {
            if (mc.player!!.distanceTo(entity) < range.get())
            {
                if (entity is PlayerEntity)
                {
                    if (0 < entity.health)
                    {
                        numA = 2
                        numB = 1
                    }
                    val posX : Double = entity.getX()
                    val posY : Double = entity.getY()
                    val posZ : Double = entity.getZ()
                    if (numA != 1 && numB == 1 && entity.health == 0f)
                    {
                        if (thunder.get())
                        {
                            for (i in 0 until numberBolts.get())
                            {
                                Spawn.entity(lightning, posX, posY, posZ)
                            }
                        }
                        if (particle.get()) Spawn.particle(particles.get(), posX, posY + 1.75 , posZ)
                        if (sound.get()) Spawn.sound(sounds.get())
                        numB = 2
                    }
                }
            }
        }
    }
}
