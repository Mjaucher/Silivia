package me.meowcher.silivia.mixins;

import me.meowcher.silivia.core.Casper;
import me.meowcher.silivia.events.DoItemUseEvent;
import me.meowcher.silivia.events.HandleBreakingEvent;
import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin ({MinecraftClient.class}) public abstract class MixinMinecraftClient
{
    @Inject (method = "getWindowTitle", at = @At (value = "RETURN"), cancellable = true)
    public void getWindowTitle(CallbackInfoReturnable<String> cir)
    {
        cir.setReturnValue(Casper.Reference.modID + "! :3");
    }

    @Redirect (method = "doItemUse", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/client/network/ClientPlayerInteractionManager;isBreakingBlock()Z"))
    public boolean doItemUse(ClientPlayerInteractionManager CPIManager)
    {
        return MeteorClient.EVENT_BUS.post(DoItemUseEvent.Companion.hook(CPIManager.isBreakingBlock())).getHook();
    }

    @Redirect (method = "handleBlockBreaking", at = @At(value = "INVOKE", target =
        "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    public boolean handleBlockBreaking(ClientPlayerEntity CPEntity)
    {
        return MeteorClient.EVENT_BUS.post(HandleBreakingEvent.Companion.hook(CPEntity.isUsingItem())).getHook();
    }
}
