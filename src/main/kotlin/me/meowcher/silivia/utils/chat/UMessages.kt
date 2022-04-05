package me.meowcher.silivia.utils.chat

import me.meowcher.silivia.utils.misc.UMinecraft.Companion.minecraft
import me.meowcher.silivia.utils.player.UInteract
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.text.Text

class UMessages
{
    companion object
    {
        fun doSend(Message : String)
        {
            UInteract.doPacketSend(ChatMessageC2SPacket(Message))
        }
    }
    object Fake
    {
        fun doSend(Message : String)
        {
            minecraft.inGameHud.chatHud.addMessage(Text.of(Message))
        }
    }
}
