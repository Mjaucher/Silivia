package rainyspring.silivia.utils.misc

import rainyspring.silivia.core.Melchior
import net.arikia.dev.drpc.DiscordEventHandlers
import net.arikia.dev.drpc.DiscordRPC
import net.arikia.dev.drpc.DiscordRichPresence

object UDiscord: Melchior {

    fun run() =
        DiscordRPC.discordInitialize(
            "907692973691654184",
            DiscordEventHandlers.Builder().setReadyEventHandler {
            }.build(), true)

    fun update(image: String, firstLine: String, secondLine: String, largeIcon: String, smallIcon: String) =
        DiscordRPC.discordUpdatePresence(
            DiscordRichPresence
                .Builder(secondLine)
                .setDetails(firstLine)
                .setBigImage(image, largeIcon)
                .setSmallImage(image, smallIcon)
                .build())

    fun shutdown() =
        DiscordRPC.discordShutdown()
}
