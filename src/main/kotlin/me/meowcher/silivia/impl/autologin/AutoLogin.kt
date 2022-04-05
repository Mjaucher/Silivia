package me.meowcher.silivia.impl.autologin

import me.meowcher.silivia.core.Initializer
import me.meowcher.silivia.utils.chat.UMessages
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

class AutoLogin : Module(Initializer.Category, "auto-login", "Enters password after logging in to server.")
{
    private val group = settings.defaultGroup
    private val password : Setting<String> = group.add(StringSetting.Builder().name("Password").defaultValue("").build())
    private var num = 0 // 😳😳😳

    override fun onActivate()
    {
        num = 1
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        if (mc.world != null && num == 1)
        {
            UMessages.doSend("/login " + password.get())
            num = 0
        }
    }
}
