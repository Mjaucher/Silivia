package silivia.impl

import silivia.Silivia
import silivia.utils.chat.Message
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

class AutoLogin : Module(Silivia.Special.Category, "Auto-Login", "Cracked server joining.")
{
    private val group = settings.defaultGroup
    private val password : Setting<String> = group.add(StringSetting.Builder().name("Password").defaultValue("").build())
    private var num = 0

    override fun onActivate()
    {
        num = 1
    }

    @EventHandler private fun onEvent(Event : Post)
    {
        if (mc.world != null && num == 1)
        {
            Message.send("/login " + password.get())
            num = 0
        }
    }
}
