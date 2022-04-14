package me.meowcher.silivia.utils.chat

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.utils.player.UInteract
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.text.Text

class UMessages
{
    companion object : Global
    {
        fun doSend(Message : String)
        {
            UInteract.doPacketSend(ChatMessageC2SPacket(Message))
        }
        fun doFakeSend(Message : String)
        {
            hud.chatHud.addMessage(Text.of(Message))
        }
    }
}
