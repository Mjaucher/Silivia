package rainyspring.silivia.impl

import rainyspring.silivia.core.Melchior
import rainyspring.silivia.core.Casper
import rainyspring.silivia.utils.chat.UMessages
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket

object AutoKit: Module(
    Casper.Reference.category,
    "auto-kit",
    "Takes Kit after your death."
), Melchior {

    private val group = settings.defaultGroup

    private val kitName = group.add(
        StringSetting.Builder().name("kit-name").defaultValue("").build()
    )

    @EventHandler
    private fun onPacketReceiveEvent(
        event: Receive
    ) {
        if (event.packet is PlayerRespawnS2CPacket && player.health == 0F)
            UMessages.doSend("/kit " + kitName.get())
    }
}
