package silivia.impl.fakekick

import silivia.Silivia
import silivia.utils.player.Interact
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket
import net.minecraft.text.LiteralText

class FakeKick : Module(Silivia.Special.Category, "Fake-Kick", "Shows a fake kick screen.")
{
    private val group = settings.defaultGroup
    private var reasonA = group.add(EnumSetting.Builder().name("Message").description("Disconnect message.")
        .defaultValue(KickEnum.Intex).build())
    private var reasonB = group.add(EnumSetting.Builder().name("Reason").description("Internal Exception Reason.")
        .defaultValue(IntexEnum.Pointer).visible{reasonA.get() == KickEnum.Intex}.build())
    private var customMessage : Setting<String> = group.add(StringSetting.Builder()
        .name("Text").defaultValue("§c☠ T§6r§eo§al§bl§9e§dd ☠").visible{reasonA.get() == KickEnum.Custom}.build())
    private var autoToggle: Setting<Boolean> = group.add(BoolSetting.Builder()
        .name("Auto-Toggle").defaultValue(true).build())

    @EventHandler private fun onEvent(Event : Post)
    {
        var iException = "internalException"
        var message = "message"
        if (reasonB.get() == IntexEnum.Pointer) iException = "Internal Exception: java.lang.NullPointerException"
        else if (reasonB.get() == IntexEnum.Closure) iException = "Internal Exception: java.io.IOException: An existing connection was forcibly closed by the remote host"
        else if (reasonB.get() == IntexEnum.Connection) iException = "Internal Exception: java.io.IOException: An established connection was aborted by the software in your host machine"
        if (reasonA.get() == KickEnum.Timeout) message = "Timed Out"
        else if (reasonA.get() == KickEnum.Flight) message = "Flying is not enabled on this server"
        else if (reasonA.get() == KickEnum.Disconnect) message = "Disconnected"
        else if (reasonA.get() == KickEnum.Intex) message = iException
        else if (reasonA.get() == KickEnum.Custom) message = customMessage.get()
        Interact.onDisconnect(DisconnectS2CPacket(LiteralText(message)))
        if (autoToggle.get()) toggle()
    }
}
