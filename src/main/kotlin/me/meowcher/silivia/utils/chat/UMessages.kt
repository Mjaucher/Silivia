package me.meowcher.silivia.utils.chat

import me.meowcher.silivia.core.Melchior
import net.minecraft.text.Text

object UMessages : Melchior {

    fun doSend(
        message : String
    ) {
        player.sendChatMessage(message)
    }

    fun doFakeSend(
        message : Text
    ) {
        chatHud.addMessage(message)
    }
}
