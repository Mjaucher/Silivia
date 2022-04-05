package me.meowcher.silivia.core

import meteordevelopment.meteorclient.MeteorClient
import meteordevelopment.meteorclient.addons.MeteorAddon
import meteordevelopment.meteorclient.systems.modules.Category
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

        Implements.modules()
    }
    override fun onRegisterCategories()
    {
        Modules.registerCategory(Category)
    }
}
