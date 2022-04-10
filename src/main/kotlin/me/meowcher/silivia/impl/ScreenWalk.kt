package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import me.meowcher.silivia.utils.player.UState
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.entity.EntityPose

class ScreenWalk : Global, Module(Initializer.Category, "screen-walk", "Allows you to perform actions when you are dead or in sleep Screen.")
{
    private val group = settings.defaultGroup
    private var sleepScreen = group.add(BoolSetting.Builder().name("sleep-screen").defaultValue(true).build())
    private var deathScreen = group.add(BoolSetting.Builder().name("death-screen").defaultValue(true).build())

    override fun onDeactivate()
    {
        if (UState.isDead) player?.requestRespawn()
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        val sleep = sleepScreen.get() && player!!.isSleeping
        val death = UState.isDead && deathScreen.get()
        if (sleep)
        {
            player?.pose = EntityPose.STANDING
            player?.clearSleepingPosition()
        }
        if (death)
        {
            player!!.health = 20F
            minecraft.setScreen(null)
            player!!.setPosition(player!!.x, player!!.y, player!!.z)
        }
        if (sleep || death) minecraft.setScreen(null)
    }
}
