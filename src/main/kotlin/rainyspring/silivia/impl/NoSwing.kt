package rainyspring.silivia.impl

import rainyspring.silivia.core.Melchior
import rainyspring.silivia.core.Casper
import meteordevelopment.meteorclient.events.packets.PacketEvent.*
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.c2s.play.*
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket

object NoSwing: Module(
    Casper.Reference.category,
    "no-swing",
    "Removes all hand Swings."
), Melchior {

    private val group = settings.defaultGroup

    private var noServerSwing = group.add(BoolSetting.Builder().name("no-server").description("Removes swings from server.").defaultValue(false).build())
    private var noForOthers = group.add(BoolSetting.Builder().name("remove-animations").description("Removes player swing animations.").defaultValue(false).build())

    @EventHandler
    private fun onPacketReceiveEvent(
        event: Receive
    ) {
        if (noServerSwing.get() && event.packet is EntityAnimationS2CPacket)
            event.isCancelled = true
    }

    @EventHandler
    private fun onPacketSendEvent(
        event: Send
    ) {
        if (noForOthers.get() && event.packet is HandSwingC2SPacket)
            event.isCancelled = true
    }

    @EventHandler
    private fun onTickPostEvent(
        event: Post
    ) {
        player.handSwinging = false
        player.handSwingProgress = 0F
        player.lastHandSwingProgress = 0F
        player.handSwingTicks = 0
    }
}
