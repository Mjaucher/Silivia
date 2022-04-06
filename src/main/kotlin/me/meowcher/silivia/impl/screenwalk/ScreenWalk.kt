package me.meowcher.silivia.impl.screenwalk

import me.meowcher.silivia.core.Initializer
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.entity.EntityPose

class ScreenWalk : Module(Initializer.Category, "screen-walk", "Allows you to perform actions when you are dead or in sleep Screen.")
{
    private val group = settings.defaultGroup
    private var sleepScreen = group.add(BoolSetting.Builder().name("sleep-screen").defaultValue(true).build())

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        val player = mc.player!!
        if (sleepScreen.get() && player.isSleeping)
        {
            player.pose = EntityPose.STANDING
            player.clearSleepingPosition()
        
        if (player.isSleeping) mc.setScreen(null)
    }
}
