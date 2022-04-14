package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import me.meowcher.silivia.utils.world.UAmbience
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.orbit.EventHandler

class NightMode : Global, Module(Initializer.Category, "night-mode", "Allows you to switch Game Time.")
{
    private val group = settings.defaultGroup
    private var status = group.add(EnumSetting.Builder().name("status").defaultValue(StatusEnum.Static).build())
    private var changeRate = group.add(IntSetting.Builder().name("change-rate").defaultValue(15).sliderRange(0, 99).visible { status.get() == StatusEnum.Changing } .build())
    private var moonAnimation = group.add(BoolSetting.Builder().name("moon-animation").defaultValue(false).visible { status.get() == StatusEnum.Changing } .build())
    private var time = group.add(IntSetting.Builder().name("time-of-day").defaultValue(18).min(0).sliderMax(24).visible { status.get() == StatusEnum.Static } .build())

    private var oldTime = 0L
    private var timeOfDay = 0

    override fun onActivate()
    {
        oldTime = UAmbience.getTime()
    }

    override fun onDeactivate()
    {
        UAmbience.setTime(oldTime)
    }

    @EventHandler private fun onPacketReceiveEvent(Event : Receive)
    {
        if (Event.packet is WorldTimeUpdateS2CPacket)
        {
            Event.isCancelled = true
        }
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        timeOfDay += if (moonAnimation.get()) 24000 else 0

        if (status.get() == StatusEnum.Static)
        {
            timeOfDay = 1000 * time.get()
            UAmbience.setTime(timeOfDay)
        }
        else
        {
            timeOfDay += 10 * changeRate.get()
            UAmbience.setTime(timeOfDay)
        }
    }

    enum class StatusEnum
    {
        Changing,
        Static
    }
}
