package me.meowcher.silivia.core

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.hud.ChatHud
import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.ClientPlayerInteractionManager
import net.minecraft.client.option.GameOptions
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.player.PlayerInventory
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

interface Melchior
{
    val minecraft : MinecraftClient get() = MinecraftClient.getInstance()
    val player : ClientPlayerEntity? get() = minecraft.player
    val world : ClientWorld? get() = minecraft.world
    val options: GameOptions get() = minecraft.options
    val network : ClientPlayNetworkHandler? get() = minecraft.networkHandler
    val inventory : PlayerInventory? get() = player?.inventory
    val hud : InGameHud get() = minecraft.inGameHud
    val chatHud : ChatHud? get() = hud.chatHud
    val interaction : ClientPlayerInteractionManager? get() = minecraft.interactionManager
    val logger : Logger? get() = LogManager.getLogger(Casper.Reference.modID)

    fun isNull() : Boolean
    {
        return world == null || player == null
    }
}
