package me.meowcher.silivia.impl.nightmode

import me.meowcher.silivia.utils.addon.Initializer
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.orbit.EventHandler

class NightMode : Module(Initializer.Category, "night-mode", "Allows you to switch Game Time.")
{
    private val group = settings.defaultGroup
    private var status = group.add(EnumSetting.Builder().name("status").defaultValue(StatusEnum.Static).build())
    private var changeRate = group.add(IntSetting.Builder().name("change-rate").defaultValue(15).sliderRange(0, 99).visible { status.get() == StatusEnum.Changing } .build())
    private var moonAnimation = group.add(BoolSetting.Builder().name("moon-animation").defaultValue(false).visible { status.get() == StatusEnum.Changing } .build())
    private var time = group.add(IntSetting.Builder().name("time-of-day").defaultValue(18).min(0).sliderMax(24).visible { status.get() == StatusEnum.Static } .build())

    private var oldTime : Long = 0
    private var timeOfDay = 0

    override fun onActivate()
    {
        oldTime = mc.world!!.time
        timeOfDay = 0
    }

    override fun onDeactivate()
    {
        mc.world!!.timeOfDay = oldTime
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
            mc.world!!.timeOfDay = timeOfDay.toLong()
        }
        else
        {
            timeOfDay += 10 * changeRate.get()
            mc.world!!.timeOfDay = timeOfDay.toLong()
        }
    }

    private enum class StatusEnum
    {
        Changing,
        Static
    }
}
