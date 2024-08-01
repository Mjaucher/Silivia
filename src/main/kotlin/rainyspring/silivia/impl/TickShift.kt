package rainyspring.silivia.impl

import rainyspring.silivia.core.Casper
import rainyspring.silivia.core.Melchior
import rainyspring.silivia.utils.world.URender
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.settings.DoubleSetting
import meteordevelopment.meteorclient.settings.IntSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.utils.player.ChatUtils
import meteordevelopment.orbit.EventHandler

object TickShift: Module(
    Casper.Reference.category,
    "tick-shift",
    "timer = 2.0"
), Melchior {

    private val group = settings.defaultGroup

    private val timerValue =
        group.add(DoubleSetting.Builder().name("timer-value").defaultValue(5.0).min(1.0).sliderMin(1.0).sliderMax(25.0).build())
    private var pauseTicks = group.add(IntSetting.Builder().name("pause-ticks").defaultValue(25).sliderRange(0, 100).build())
    private var timerTicks = group.add(IntSetting.Builder().name("timer-ticks").defaultValue(15).sliderRange(0, 100).build())
    private var autoToggle = group.add(BoolSetting.Builder().name("auto-toggle").defaultValue(true).build())

    private var tickShiftDone = false
    private var notificationDeath = false
    private var passivePauseTicks = 0
    private var activePauseTicks = 0

    override fun onActivate() = default()
    override fun onDeactivate() = default()

    @EventHandler
    private fun onTickPostEvent(
        event: Post
    ) {
        val whenPlayerMove = options.forwardKey.isPressed
            || options.leftKey.isPressed
            || options.rightKey.isPressed
            || options.backKey.isPressed

        if (!tickShiftDone && !whenPlayerMove)
            passivePauseTicks++

        if (whenPlayerMove)
            passivePauseTicks = 0

        if (pauseTicks.get() <= passivePauseTicks || tickShiftDone) {

            tickShiftDone = true

            if (!notificationDeath)
                notification()

            if (activePauseTicks >= 0 && whenPlayerMove) {
                URender.tickCounter = timerValue.get()
                activePauseTicks--
            }
        }

        if (tickShiftDone && activePauseTicks <= 0) {

            default()

            if (autoToggle.get())
                toggle()
        }
    }

    private fun default() {

        tickShiftDone = false
        notificationDeath = false
        passivePauseTicks = 0
        activePauseTicks = timerTicks.get()
        URender.tickCounter = 1.0
    }

    private fun notification() {

        ChatUtils.info("> Tick Shift is ready!")
        notificationDeath = true
    }
}
