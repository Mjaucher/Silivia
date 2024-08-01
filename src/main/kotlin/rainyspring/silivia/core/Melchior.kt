package rainyspring.silivia.core

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.hud.*
import net.minecraft.client.network.*
import net.minecraft.client.option.GameOptions
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.player.PlayerInventory
import org.apache.logging.log4j.*

interface Melchior {

    val minecraft: MinecraftClient get() = MinecraftClient.getInstance()
    val player: ClientPlayerEntity get() = minecraft.player!!
    val world: ClientWorld get() = minecraft.world!!
    val options: GameOptions get() = minecraft.options
    val network: ClientPlayNetworkHandler get() = minecraft.networkHandler!!
    val inventory: PlayerInventory get() = player.inventory!!
    val hud: InGameHud get() = minecraft.inGameHud
    val chatHud: ChatHud get() = hud.chatHud
    val logger: Logger get() = LogManager.getLogger(Casper.Reference.name)
    val interaction: ClientPlayerInteractionManager
        get() = minecraft.interactionManager!!
}
