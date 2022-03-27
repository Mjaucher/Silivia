package silivia.utils.chat

import silivia.Silivia.Special.minecraft
import silivia.utils.player.Interact
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.text.Text

class Message
{
    companion object
    {
        fun send(Message : String)
        {
            Interact.packetSend(ChatMessageC2SPacket(Message))
        }
    }
    object Fake
    {
        fun send(Message : String)
        {
            minecraft.inGameHud.chatHud.addMessage(Text.of(Message))
        }
    }
}
