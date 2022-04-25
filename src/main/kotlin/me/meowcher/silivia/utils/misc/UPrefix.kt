package me.meowcher.silivia.utils.misc

import me.meowcher.silivia.core.Casper
import net.minecraft.text.LiteralText

class UPrefix
{
    companion object
    {
        private var prefix = LiteralText(Casper.Reference.modID)

        fun setPrefix(LiteralText : LiteralText)
        {
            prefix = LiteralText
        }

        fun getPrefix() : LiteralText
        {
            return prefix
        }
    }
}
