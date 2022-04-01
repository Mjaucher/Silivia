package me.meowcher.silivia.utils.addon

import me.meowcher.silivia.impl.autokit.AutoKit
import me.meowcher.silivia.impl.autologin.AutoLogin
import me.meowcher.silivia.impl.multitask.MultiTask
import me.meowcher.silivia.impl.fakekick.FakeKick
import me.meowcher.silivia.impl.killeffects.KillEffects
import me.meowcher.silivia.impl.nightmode.NightMode
import me.meowcher.silivia.impl.nointeract.NoInteract
import me.meowcher.silivia.impl.prefix.Prefix
import me.meowcher.silivia.impl.surround.Surround
import meteordevelopment.meteorclient.MeteorClient
import meteordevelopment.meteorclient.addons.MeteorAddon
import meteordevelopment.meteorclient.systems.modules.Category
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.systems.modules.Modules
import org.slf4j.LoggerFactory
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

class Initializer : MeteorAddon()
{
    companion object
    {
        val Category : Category = Category(Reference.Name)
    }
    override fun onInitialize()
    {
        LoggerFactory.getLogger(Reference.Name).info("\n" +
            "░██████╗██╗██╗░░░░░██╗██╗░░░██╗██╗░█████╗░░██╗\n" +
            "██╔════╝██║██║░░░░░██║██║░░░██║██║██╔══██╗░██║\n" +
            "╚█████╗░██║██║░░░░░██║╚██╗░██╔╝██║███████║░██║\n" +
            "░╚═══██╗██║██║░░░░░██║░╚████╔╝░██║██╔══██║░╚═╝\n" +
            "██████╔╝██║███████╗██║░░╚██╔╝░░██║██║░░██║░██╗\n" +
            "╚═════╝░╚═╝╚══════╝╚═╝░░░╚═╝░░░╚═╝╚═╝░░╚═╝░╚═╝")

        MeteorClient.EVENT_BUS.registerLambdaFactory("me.meowcher.silivia") { lookupInMethod : Method, klass :
        Class<*>? -> lookupInMethod.invoke(null, klass, MethodHandles.lookup()) as MethodHandles.Lookup }

        registerModules(
            KillEffects(),
            NoInteract(),
            NightMode(),
            AutoLogin(),
            MultiTask(),
            FakeKick(),
            AutoKit(),
            Prefix(),
        )
    }
    override fun onRegisterCategories()
    {
        Modules.registerCategory(Category)
    }
    private fun registerModules(vararg Features : Module?)
    {
        for (modules in Features)
        {
            Modules.get().add(modules)
        }
    }
}
