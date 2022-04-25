package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import me.meowcher.silivia.utils.chat.UMessages
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

class AutoLogin : Melchior, Module(Casper.Reference.category, "auto-login", "Enters password after logging in to server.")
{
    private val group = settings.defaultGroup
    private val password = group.add(StringSetting.Builder().name("Password").defaultValue("").build())

    private var num = 0

    override fun onActivate()
    {
        num = 1
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        if (world != null && num == 1)
        {
            UMessages.doSend("/login " + password.get())
            num = 0
        }
    }
}
