package rainyspring.silivia.utils.chat

import rainyspring.silivia.core.Melchior
import net.minecraft.text.Text

object UMessages: Melchior {

    fun doSend(
        message: String
    ) {
        player.networkHandler.sendChatMessage(message)
    }

    fun doFakeSend(
        message: Text
    ) {
        chatHud.addMessage(message)
    }
}
