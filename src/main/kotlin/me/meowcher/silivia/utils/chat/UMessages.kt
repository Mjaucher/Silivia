package me.meowcher.silivia.utils.chat

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.utils.player.UInteract
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket
import net.minecraft.text.LiteralText
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting

class UMessages
{
    companion object : Melchior
    {
        fun doSend(Message : String)
        {
            UInteract.doPacketSend(ChatMessageC2SPacket(Message))
        }

        fun doFakeSend(Message : LiteralText)
        {
            chatHud?.addMessage(Message)
        }

        fun getText(Text : LiteralText, Color : TextColor, Formatting : Formatting) : LiteralText
        {
            Text.style = Text.style.withColor(Color)
            Text.style = Text.style.withFormatting(Formatting)
            return Text
        }
    }
}
