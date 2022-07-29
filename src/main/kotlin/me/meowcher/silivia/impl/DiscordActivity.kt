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

    private val group = settings.defaultGroup

    private val firstLine = group.add(StringSetting.Builder().name("first-line")
        .defaultValue("https://github.com/mjaucher/Silivia")
        .build())
    private val secondLine = group.add(StringSetting.Builder().name("second-line")
        .defaultValue("Minecraft: ${minecraft.gameVersion} ${SharedConstants.getGameVersion().name}")
        .build())

    override fun onActivate() {
        UDiscord.run()
        UDiscord.update("general-icon", firstLine.get(), secondLine.get())
    }
    override fun onDeactivate() = UDiscord.shutdown()

    override fun getWidget(guiTheme: GuiTheme): WWidget {
        val updateButton = guiTheme.button("Update info!")
        updateButton.action = Runnable {
            UDiscord.update("general-icon", firstLine.get(), secondLine.get())
        }
        return updateButton
    }
}
