package silivia.impl.autokit

import silivia.Silivia
import silivia.utils.chat.Message
import meteordevelopment.meteorclient.events.packets.PacketEvent.Receive
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket

class AutoKit : Module(Silivia.Special.Category, "Auto-Kit", "Enters /kit (Kit Name) after respawn.")
{
    private val group = settings.defaultGroup
    private val kitName : Setting<String> = group.add(StringSetting.Builder().name("Kit-Name").defaultValue("").build())

    @EventHandler private fun onEvent(Event : Receive)
    {
        if (Event.packet is PlayerRespawnS2CPacket
            && mc.player!!.health == 0f)
        {
            Message.send("/kit " + kitName.get())
        }
    }
}
