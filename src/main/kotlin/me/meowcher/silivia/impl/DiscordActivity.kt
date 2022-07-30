package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import me.meowcher.silivia.utils.misc.UDiscord
import meteordevelopment.meteorclient.gui.GuiTheme
import meteordevelopment.meteorclient.gui.widgets.WWidget
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.settings.*
import net.minecraft.SharedConstants

object DiscordActivity : Module(
    Casper.Reference.category,
    "discord-activity",
    "Displays your discord activity."
), Melchior {

    private var linesGroup = settings.createGroup("Lines Settings")
    private var iconsGroup = settings.createGroup("Icons Settings")

    private val firstLine = linesGroup.add(StringSetting.Builder().name("first-line")
        .defaultValue("Minecraft: ${minecraft.gameVersion} ${SharedConstants.getGameVersion().name}")
        .build())
    private val secondLine = linesGroup.add(StringSetting.Builder().name("second-line")
        .defaultValue("Addon Version: ${Casper.Reference.modVersion}")
        .build())

    private val largeIcon = iconsGroup.add(StringSetting.Builder().name("large-icon")
        .defaultValue(Casper.Reference.modVersion)
        .build())
    private val smallIcon = iconsGroup.add(StringSetting.Builder().name("small-icon")
        .defaultValue("<3")
        .build())

    override fun onActivate() {
        UDiscord.run()
        update()
    }
    override fun onDeactivate() {
        update()
        UDiscord.shutdown()
    }

    override fun getWidget(guiTheme: GuiTheme): WWidget {
        val updateButton = guiTheme.button("Update info!")
        updateButton.action = Runnable {
            update()
        }
        return updateButton
    }

    private fun update() =
        UDiscord.update(
            "general-icon",
            firstLine.get(),
            secondLine.get(),
            largeIcon.get(),
            smallIcon.get()
        )
}
