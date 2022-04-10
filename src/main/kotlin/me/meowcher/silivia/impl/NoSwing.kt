package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket

class NoSwing : Global, Module(Initializer.Category, "no-swing", "Removes all hand Swings.")
{
    private val group = settings.defaultGroup
    private var noServerSwing = group.add(BoolSetting.Builder().name("no-server")
        .description("Cancel swings from server.").defaultValue(false).build())

    @EventHandler private fun onPacketReceiveEvent(Event : Receive)
    {
        if (noServerSwing.get() && Event.packet is EntityAnimationS2CPacket)
        {
            Event.isCancelled = true
        }
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        player!!.handSwinging = false
        player!!.handSwingProgress = 0F
        player!!.lastHandSwingProgress = 0F
        player!!.handSwingTicks = 0
    }
}
