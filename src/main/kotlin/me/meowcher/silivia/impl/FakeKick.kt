package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket
import net.minecraft.text.LiteralText

class FakeKick : Global, Module(Initializer.Category, "fake-kick", "Automatic disconnect with Fake reason.")
{
    private val group = settings.defaultGroup
    private var disconnectReason = group.add(EnumSetting.Builder().name("message").description("Disconnect message.").defaultValue(
        KickEnum.Intex
    ).build())
    private var internalExceptionReason = group.add(EnumSetting.Builder().name("reason").description("Internal Exception Reason.").defaultValue(
        IntexEnum.Connection
    ).visible { disconnectReason.get() == KickEnum.Intex } .build())
    private var customMessage = group.add(StringSetting.Builder().name("text").defaultValue("§c☠ T§6r§eo§al§bl§9e§dd ☠").visible { disconnectReason.get() == KickEnum.Custom } .build())
    private var autoToggle = group.add(BoolSetting.Builder().name("auto-toggle").defaultValue(true).build())

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        val iE = "Internal Exception: java."
        val iException = when (internalExceptionReason.get())
        {
            IntexEnum.Closure -> iE+"io.IOException: An existing connection was forcibly closed by the remote host"
            IntexEnum.Pointer -> iE+"lang.NullPointerException"
            else -> iE+"io.IOException: An established connection was aborted by the software in your host machine"
        }
        val disconnectMessage = when (disconnectReason.get())
        {
            KickEnum.Disconnect -> "Disconnected"
            KickEnum.WrongAuth -> "Not authenticated with Minecraft.net"
            KickEnum.Timeout -> "Timed Out"
            KickEnum.Flight -> "Flying is not enabled on this server"
            KickEnum.Verify -> "Failed to verify username"
            KickEnum.Intex -> iException
            else -> customMessage.get()
        }

        network?.onDisconnect(DisconnectS2CPacket(LiteralText(disconnectMessage)))
        if (autoToggle.get()) toggle()
    }

    enum class IntexEnum
    {
        Connection, Pointer, Closure
    }

    enum class KickEnum
    {
        Disconnect, WrongAuth, Timeout, Flight, Verify, Custom, Intex
    }
}
