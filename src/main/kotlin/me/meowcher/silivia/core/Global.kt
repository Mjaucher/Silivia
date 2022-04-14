package me.meowcher.silivia.core

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.network.ClientPlayerInteractionManager
import net.minecraft.client.option.GameOptions
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.player.PlayerInventory

interface Global
{
    val minecraft : MinecraftClient get() = MinecraftClient.getInstance()
    val player : ClientPlayerEntity? get() = minecraft.player
    val world : ClientWorld? get() = minecraft.world
    val options: GameOptions get() = minecraft.options
    val network : ClientPlayNetworkHandler? get() = minecraft.networkHandler
    val inventory : PlayerInventory? get() = player?.inventory
    val hud : InGameHud get() = minecraft.inGameHud
    val interaction : ClientPlayerInteractionManager? get() = minecraft.interactionManager
}
