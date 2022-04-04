package me.meowcher.silivia.impl.noswing

import me.meowcher.silivia.utils.addon.Initializer
import meteordevelopment.meteorclient.events.packets.PacketEvent
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket

class NoSwing : Module(Initializer.Category, "no-swing", "Removes Swing hands.")
{
    @EventHandler private fun onPacketReceiveEvent(Event : PacketEvent.Receive)
    {
        if (Event.packet is EntityAnimationS2CPacket)
        {
            Event.isCancelled = true
        }
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        val pl = mc.player!!
        pl.handSwinging = false
        pl.handSwingProgress = 0F
        pl.handSwingTicks = 0
    }
}
