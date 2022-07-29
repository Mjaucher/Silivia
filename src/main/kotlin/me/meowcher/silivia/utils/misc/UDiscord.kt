package me.meowcher.silivia.utils.misc

import me.meowcher.silivia.core.Melchior
import net.arikia.dev.drpc.DiscordEventHandlers
import net.arikia.dev.drpc.DiscordRPC
import net.arikia.dev.drpc.DiscordRichPresence

object UDiscord: Melchior {

    fun run() =
        DiscordRPC.discordInitialize(
            "907692973691654184",
            DiscordEventHandlers.Builder().setReadyEventHandler {
            }.build(), true)

    fun update(image: String, firstLine: String, secondLine: String) =
        DiscordRPC.discordUpdatePresence(
            DiscordRichPresence
                .Builder(secondLine)
                .setDetails(firstLine)
                .setBigImage(image, "Silivia!")
                .build())

    fun shutdown() =
        DiscordRPC.discordShutdown()
}
