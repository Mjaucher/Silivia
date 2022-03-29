package silivia.impl.prefix

import silivia.Silivia
import silivia.utils.Reference
import silivia.impl.prefix.FormatEnum.*
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.utils.player.ChatUtils.registerCustomPrefix
import meteordevelopment.meteorclient.utils.render.color.SettingColor
import net.minecraft.text.LiteralText
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting.*

class Prefix : Module(Silivia.Special.Category, "prefix", "Adds customization for prefix from Meteor Client!")
{
    private val group = settings.defaultGroup
    private var colorGroup = settings.createGroup("Color Settings")
    private var formatting = group.add(EnumSetting.Builder().name("formatting").defaultValue(Bold).build())
    private val sideA = group.add(StringSetting.Builder().name("first-side").defaultValue("$").build())
    private val text = group.add(StringSetting.Builder().name("prefix-text").defaultValue(Reference.Name).build())
    private val sideB = group.add(StringSetting.Builder().name("second-side").defaultValue("$").build())
    private val colorA = colorGroup.add(ColorSetting.Builder().name("first-color").defaultValue(SettingColor(0, 100, 150, 255)).build())
    private val colorB = colorGroup.add(ColorSetting.Builder().name("text-color").defaultValue(SettingColor(0, 125, 175, 255)).build())
    private val colorC = colorGroup.add(ColorSetting.Builder().name("second-color").defaultValue(SettingColor(0, 150, 200, 255)).build())

    override fun onActivate()
    {
        registerCustomPrefix("meteordevelopment.meteorclient") {style()}
        registerCustomPrefix("silivia") {style()}
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
            else -> RESET
        }
        val prefix = LiteralText("")

        val sideA = LiteralText(sideA.get())
        prefix.append(sideA).style = sideA.style.withColor(TextColor.fromRgb(colorA.get().packed)).withFormatting(format)
        val name = LiteralText(text.get())
        prefix.append(name).style = name.style.withColor(TextColor.fromRgb(colorB.get().packed)).withFormatting(format)
        val sideB = LiteralText(sideB.get())
        prefix.append(sideB).style = sideB.style.withColor(TextColor.fromRgb(colorC.get().packed)).withFormatting(format)

        return prefix
    }
}
