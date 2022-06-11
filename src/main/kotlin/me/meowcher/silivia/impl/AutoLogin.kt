package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import me.meowcher.silivia.utils.chat.UMessages
import me.meowcher.silivia.utils.misc.UVariables
import meteordevelopment.meteorclient.events.packets.PacketEvent
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket

object AutoLogin : Module(
    Casper.Reference.category,
    "auto-login",
    "Automatic registration and login to the server."
), Melchior {

    private val group = settings.defaultGroup

    private val password =
        group.add(StringSetting.Builder().name("password").defaultValue("1234567890").build())
    private var registration =
        group.add(BoolSetting.Builder().name("registration").defaultValue(false).build())

    private var onActivate = false

    override fun onActivate() {

        onActivate = true
    }

    @EventHandler
    private fun onPacketSendEvent(
        Event : PacketEvent.Send
    ) {
        if (Event.packet is GameJoinS2CPacket || onActivate) {

            IntRange(if (registration.get()) 1 else 2, 2).forEach {

                val pass = UVariables.repeat(password.get(), it, true)

                UMessages.doSend("/${
                    if (it == 1) "register "
                    else "login "}$pass"
                )
            }

            onActivate = false
        }
    }
}
