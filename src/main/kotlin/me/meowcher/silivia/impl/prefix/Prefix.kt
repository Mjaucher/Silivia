package me.meowcher.silivia.impl.prefix

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import me.meowcher.silivia.core.Reference
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.utils.player.ChatUtils
import net.minecraft.text.LiteralText
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting.*

class Prefix : Global, Module(Initializer.Category, "prefix", "Custom Meteor Client Prefix with more customizations.")
{
    private val group = settings.defaultGroup
    private var colorGroup = settings.createGroup("Color Settings")
    private var formatting = group.add(EnumSetting.Builder().name("formatting").defaultValue(TextStyle.None).build())
    private val leftSide = group.add(StringSetting.Builder().name("left-side").defaultValue("$").build())
    private val middleText = group.add(StringSetting.Builder().name("middle-text").defaultValue(Reference.Name).build())
    private val rightSide = group.add(StringSetting.Builder().name("right-side").defaultValue("$ ").build())
    private val leftColor = colorGroup.add(ColorSetting.Builder().name("left-color").defaultValue(Reference.thirdDefColor).build())
    private val middleColor = colorGroup.add(ColorSetting.Builder().name("middle-color").defaultValue(Reference.secondDefColor).build())
    private val rightColor = colorGroup.add(ColorSetting.Builder().name("right-color").defaultValue(Reference.firstDefColor).build())

    override fun onActivate()
    {
        ChatUtils.registerCustomPrefix("meteordevelopment.meteorclient") { prefixStyle() }
        ChatUtils.registerCustomPrefix("me.meowcher.silivia") { prefixStyle() }
    }

    private fun prefixStyle() : LiteralText
    {
        val leftSide = LiteralText(leftSide.get())
        val middleText = LiteralText(middleText.get())
        val rightSide = LiteralText(rightSide.get())

        val prefix = LiteralText("")

        val formatting = when (formatting.get())
        {
            TextStyle.Strikethrough -> STRIKETHROUGH
            TextStyle.Obfuscated -> OBFUSCATED
            TextStyle.Underline -> UNDERLINE
            TextStyle.Italic -> ITALIC
            TextStyle.Bold -> BOLD
            else -> WHITE
        }

        leftSide.style = leftSide.style.withColor(TextColor.fromRgb(leftColor.get().packed))
        middleText.style = middleText.style.withColor(TextColor.fromRgb(middleColor.get().packed))
        rightSide.style = rightSide.style.withColor(TextColor.fromRgb(rightColor.get().packed))

        prefix.append(leftSide).style = leftSide.style.withFormatting(formatting)
        prefix.append(middleText).style = middleText.style.withFormatting(formatting)
        prefix.append(rightSide).style = rightSide.style.withFormatting(formatting)

        return prefix
    }

    enum class TextStyle
    {
        Strikethrough,
        Obfuscated,
        Underline,
        Italic,
        Bold,
        None
    }
}
