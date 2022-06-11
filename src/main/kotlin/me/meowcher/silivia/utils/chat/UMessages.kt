package me.meowcher.silivia.utils.chat

import me.meowcher.silivia.core.Melchior
import net.minecraft.text.Text

class UMessages
{
    companion object : Melchior
    {
        fun doSend(Message : String)
        {
            player.sendChatMessage(Message)
        }

        fun doFakeSend(Message : Text)
        {
            chatHud.addMessage(Message)
        }
    }
}
