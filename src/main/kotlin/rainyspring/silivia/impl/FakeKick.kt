package rainyspring.silivia.impl

import rainyspring.silivia.core.*
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket
import net.minecraft.text.Text
import java.io.IOException

enum class IntExEnum
{
    Connection,
    Pointer,
    Closure
}

enum class KickEnum
{
    Disconnect,
    WrongAuth,
    Timeout,
    Flight,
    Verify,
    Custom,
    IntEx
}

object FakeKick: Module(
    Casper.Reference.category,
    "fake-kick",
    "Automatic disconnect with Fake reason."
), Melchior {

    private val group = settings.defaultGroup

    private var disconnectReason = group.add(EnumSetting.Builder().name("message").description("Disconnect message.").defaultValue(KickEnum.IntEx).build())
    private var internalExceptionReason = group.add(EnumSetting.Builder().name("reason").description("Internal Exception Reason.").defaultValue(IntExEnum.Connection).visible { disconnectReason.get() == KickEnum.IntEx } .build())
    private var customMessage = group.add(StringSetting.Builder().name("text").defaultValue("§c☠ T§6r§eo§al§bl§9e§dd ☠").visible { disconnectReason.get() == KickEnum.Custom } .build())
    private var autoToggle = group.add(BoolSetting.Builder().name("auto-toggle").defaultValue(true).build())

    @EventHandler
    private fun onTickPostEvent(
        event: Post
    ) {
        network.onDisconnect(DisconnectS2CPacket(Text.literal(
            when (disconnectReason.get()) {

                KickEnum.Disconnect -> "Disconnected"
                KickEnum.WrongAuth -> "Not authenticated with Minecraft.net"
                KickEnum.Timeout -> "Timed Out"
                KickEnum.Flight -> "Flying is not enabled on this server"
                KickEnum.Verify -> "Failed to verify username"
                KickEnum.IntEx -> "Internal Exception: " + when (internalExceptionReason.get()) {
                    IntExEnum.Closure ->
                        "${IOException()}: An existing connection was forcibly closed by the remote host"
                    IntExEnum.Pointer ->
                        NullPointerException().toString()
                    else ->
                        "${IOException()}: An established connection was aborted by the software in your host machine"
                }
                else ->
                    customMessage.get()
            }
        ))
        )

        if (autoToggle.get())
            toggle()
    }
}
