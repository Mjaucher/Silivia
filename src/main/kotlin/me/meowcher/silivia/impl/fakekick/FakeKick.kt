package me.meowcher.silivia.impl.fakekick

import me.meowcher.silivia.utils.addon.Initializer
import me.meowcher.silivia.utils.player.Interact
import me.meowcher.silivia.impl.fakekick.KickEnum.*
import me.meowcher.silivia.impl.fakekick.IntexEnum.*
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket
import net.minecraft.text.LiteralText

class FakeKick : Module(Initializer.Category, "fake-kick", "Shows a fake kick screen.")
{
    private val group = settings.defaultGroup
    private var reasonA = group.add(EnumSetting.Builder().name("message").description("Disconnect message.").defaultValue(Intex).build())
    private var reasonB = group.add(EnumSetting.Builder().name("reason").description("Internal Exception Reason.").defaultValue(Pointer).visible{reasonA.get() == Intex}.build())
    private var customMessage = group.add(StringSetting.Builder().name("text").defaultValue("§c☠ T§6r§eo§al§bl§9e§dd ☠").visible{reasonA.get() == Custom}.build())
    private var autoToggle = group.add(BoolSetting.Builder().name("auto-toggle").defaultValue(true).build())

    @EventHandler private fun onEventA(Event : Post)
    {
        val iE = "Internal Exception: java."
        val iException = when (reasonB.get())
        {
            Closure -> iE+"io.IOException: An existing connection was forcibly closed by the remote host"
            Pointer -> iE+"lang.NullPointerException"
            else -> iE+"io.IOException: An established connection was aborted by the software in your host machine"
        }
        val message = when (reasonA.get())
        {
            Disconnect -> "Disconnected"
            WrongAuth -> "Not authenticated with Minecraft.net"
            Timeout -> "Timed Out"
            Flight -> "Flying is not enabled on this server"
            Verify -> "Failed to verify username"
            Intex -> iException
            else -> customMessage.get()
        }
        Interact.onDisconnect(DisconnectS2CPacket(LiteralText(message)))
        if (autoToggle.get()) toggle()
    }
}
