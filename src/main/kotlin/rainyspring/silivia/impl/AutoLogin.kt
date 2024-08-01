package rainyspring.silivia.impl

import meteordevelopment.meteorclient.events.game.GameJoinedEvent
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import rainyspring.silivia.core.Casper
import rainyspring.silivia.core.Melchior
import rainyspring.silivia.utils.chat.UMessages
import rainyspring.silivia.utils.misc.UVariables

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

    override fun onActivate() {

        login()
    }

    @EventHandler
    private fun onGameJoined(event: GameJoinedEvent) {

        login()
    }

    private fun login() {

        IntRange(if (registration.get()) 1 else 2, 2).forEach {

            val pass = UVariables.repeat(password.get(), 3 - it, true)

            UMessages.doSend(
                "/${
                    if (it == 1) "register "
                    else "login "
                }$pass"
            )
        }
    }
}
