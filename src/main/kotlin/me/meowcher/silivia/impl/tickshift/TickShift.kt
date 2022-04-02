package me.meowcher.silivia.impl.tickshift

import me.meowcher.silivia.utils.addon.Initializer
import me.meowcher.silivia.utils.misc.Minecraft.Companion.minecraft
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.settings.DoubleSetting
import meteordevelopment.meteorclient.settings.IntSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.systems.modules.Modules
import meteordevelopment.meteorclient.systems.modules.world.Timer
import meteordevelopment.meteorclient.utils.player.ChatUtils
import meteordevelopment.orbit.EventHandler

class TickShift : Module(Initializer.Category, "tick-shift", "timer = 2.0")
{
    private val group = settings.defaultGroup
    private val timerValue = group.add(DoubleSetting.Builder().name("timer-value").defaultValue(5.0).min(1.0).sliderMin(1.0).sliderMax(25.0).build())
    private var pauseTicks = group.add(IntSetting.Builder().name("pause-ticks").defaultValue(50).sliderRange(0, 100).build())
    private var timerTicks = group.add(IntSetting.Builder().name("timer-ticks").defaultValue(50).sliderRange(0, 100).build())
    private var autoToggle = group.add(BoolSetting.Builder().name("auto-toggle").defaultValue(true).build())

    private var booB = false
    private var booC = false
    private var ticksA = 0
    private var ticksB = 0

    private fun default()
    {
        timer(1.0)
        booB = false
        booC = false
        ticksA = 0
        ticksB = timerTicks.get()
    }

    override fun onActivate()
    {
        default()
    }
    override fun onDeactivate()
    {
        default()
    }

    @EventHandler private fun onEventA(Event : Post)
    {
        //val booA = minecraft.player!!.sidewaysSpeed != 0F || minecraft.player!!.forwardSpeed != 0F
        val booA = mc.options.forwardKey.isPressed||mc.options.leftKey.isPressed||mc.options.rightKey.isPressed||mc.options.backKey.isPressed

        if (!booB && !booA) ticksA++
        if (booA) ticksA = 0

        if (pauseTicks.get() <= ticksA || booB)
        {
            booB = true
            if (!booC) message()
            if (ticksB >= 0 && booA)
            {
                timer(timerValue.get())
                ticksB--
            }
        }

        if (booB && ticksB <= 0)
        {
            if (autoToggle.get()) toggle()
            default()
        }
    }

    private fun timer(Value : Double)
    {
        Modules.get().get(Timer::class.java).setOverride(Value)
    }
    private fun message()
    {
        ChatUtils.info("> Tick Shift is ready!")
        booC = true
    }
}
