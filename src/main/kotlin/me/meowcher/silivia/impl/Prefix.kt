package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.utils.player.ChatUtils
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting

enum class TextStyle {
    STRIKETHROUGH,
    OBFUSCATED,
    UNDERLINE,
    ITALIC,
    RESET,
    BOLD
}

object Prefix : Module(
    Casper.Reference.category,
    "prefix",
    "Custom Meteor Client Prefix with more customizations."
), Melchior {

    private val group = settings.defaultGroup
    private var colorGroup = settings.createGroup("Color Settings")

    private var formatting = group.add(EnumSetting.Builder().name("formatting").defaultValue(TextStyle.RESET).build())
    private val leftSide = group.add(StringSetting.Builder().name("left-side").defaultValue("$").build())
    private val middleText = group.add(StringSetting.Builder().name("middle-text").defaultValue(Casper.Reference.modID).build())
    private val rightSide = group.add(StringSetting.Builder().name("right-side").defaultValue("$ ").build())
    private val leftColor = colorGroup.add(ColorSetting.Builder().name("left-color").defaultValue(Casper.Reference.defaultColorAlphaPlus).build())
    private val middleColor = colorGroup.add(ColorSetting.Builder().name("middle-color").defaultValue(Casper.Reference.defaultColorAlpha).build())
    private val rightColor = colorGroup.add(ColorSetting.Builder().name("right-color").defaultValue(Casper.Reference.defaultColor).build())

    override fun onActivate() {

        ChatUtils.registerCustomPrefix("meteordevelopment.meteorclient") { prefixStyle() }
        ChatUtils.registerCustomPrefix("me.meowcher.silivia") { prefixStyle() }
    }

    private fun prefixStyle() : Text {

        val leftSide = Text.literal(leftSide.get())
        val middleText = Text.literal(middleText.get())
        val rightSide = Text.literal(rightSide.get())

        val prefix = Text.literal("")

        val style = Formatting.byName(formatting.get().toString())

        leftSide.style = leftSide.style.withColor(TextColor.fromRgb(leftColor.get().packed))
        middleText.style = middleText.style.withColor(TextColor.fromRgb(middleColor.get().packed))
        rightSide.style = rightSide.style.withColor(TextColor.fromRgb(rightColor.get().packed))

        prefix.append(leftSide).style = leftSide.style.withFormatting(style)
        prefix.append(middleText).style = middleText.style.withFormatting(style)
        prefix.append(rightSide).style = rightSide.style.withFormatting(style)

        return prefix
    }
}
