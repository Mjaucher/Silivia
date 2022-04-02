package me.meowcher.silivia.mixins;

import me.meowcher.silivia.events.BreakingBlock;
import me.meowcher.silivia.events.UsingItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import meteordevelopment.meteorclient.MeteorClient;

@Mixin (value = MinecraftClient.class, priority = 1001) public abstract class Minecraft
{
    @Redirect (method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;isBreakingBlock()Z"))
    public boolean doItemUse(ClientPlayerInteractionManager CPIManager)
    {
        return MeteorClient.EVENT_BUS.post(BreakingBlock.Companion.hook(CPIManager.isBreakingBlock())).getBoo();
    }
    @Redirect (method = "handleBlockBreaking", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    public boolean handleBlockBreaking(ClientPlayerEntity CPEntity)
    {
        return MeteorClient.EVENT_BUS.post(UsingItem.Companion.hook(CPEntity.isUsingItem())).getLean();
    }
}
