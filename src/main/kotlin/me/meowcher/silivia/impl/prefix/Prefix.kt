package me.meowcher.silivia.impl.prefix

import me.meowcher.silivia.utils.addon.Initializer
import me.meowcher.silivia.impl.prefix.FormatEnum.*
import me.meowcher.silivia.utils.addon.Reference
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.utils.player.ChatUtils.registerCustomPrefix
import meteordevelopment.meteorclient.utils.render.color.SettingColor
import net.minecraft.text.LiteralText
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting.*


class Prefix : Module(Initializer.Category, "prefix", "Adds customization for prefix from Meteor Client!")
{
    private val group = settings.defaultGroup
    private var colorGroup = settings.createGroup("Color Settings")
    private var formatting = group.add(EnumSetting.Builder().name("formatting").defaultValue(Bold).build())
    private val sideA = group.add(StringSetting.Builder().name("first-side").defaultValue("$").build())
    private val text = group.add(StringSetting.Builder().name("prefix-text").defaultValue(Reference.Name).build())
    private val sideB = group.add(StringSetting.Builder().name("second-side").defaultValue("$ ").build())
    private val colorA = colorGroup.add(ColorSetting.Builder().name("first-color").defaultValue(Reference.defaultColorA).build())
    private val colorB = colorGroup.add(ColorSetting.Builder().name("text-color").defaultValue(Reference.defaultColorB).build())
    private val colorC = colorGroup.add(ColorSetting.Builder().name("second-color").defaultValue(Reference.defaultColorC).build())

    override fun onActivate()
    {
        registerCustomPrefix("meteordevelopment.meteorclient") {style()}
        registerCustomPrefix("me.meowcher.silivia") {style()}
    }
    private fun style() : LiteralText
    {
        val format = when (formatting.get())
        {
            Strikethrough -> STRIKETHROUGH
            Obfuscated -> OBFUSCATED
            Underline -> UNDERLINE
            Italic -> ITALIC
            Bold -> BOLD
            else -> WHITE
        }

        val sideA = LiteralText(sideA.get())
        val name = LiteralText(text.get())
        val sideB = LiteralText(sideB.get())

        val prefix = LiteralText("")

        sideA.style = sideA.style.withColor(TextColor.fromRgb(colorA.get().packed))
        name.style = name.style.withColor(TextColor.fromRgb(colorB.get().packed))
        sideB.style = sideB.style.withColor(TextColor.fromRgb(colorC.get().packed))

        prefix.append(sideA).style = sideA.style.withFormatting(format)
        prefix.append(name).style = name.style.withFormatting(format)
        prefix.append(sideB).style = sideB.style.withFormatting(format)

        return prefix
    }
}
