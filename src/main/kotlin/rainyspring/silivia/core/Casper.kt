package rainyspring.silivia.core

import meteordevelopment.meteorclient.addons.MeteorAddon
import meteordevelopment.meteorclient.systems.modules.Category
import meteordevelopment.meteorclient.systems.modules.Modules
import meteordevelopment.meteorclient.utils.render.color.SettingColor


class Casper: Melchior, MeteorAddon()
{
    object Reference
    {
        const val name = "Silivia"
        const val version = "v1"

        val category: Category = Category(name)

        val defaultColor = SettingColor(165, 125, 255, 255)

        val defaultColorAlpha = SettingColor(
            defaultColor.r - 30,
            defaultColor.g - 20,
            defaultColor.b - 50, 255
        )
        val defaultColorAlphaPlus = SettingColor(
            defaultColor.r - 60,
            defaultColor.g - 20,
            defaultColor.b - 95, 255
        )
    }

    override fun onRegisterCategories()
    {
        Modules.registerCategory(Reference.category)
        logger.info("Categories are registered!")
    }

    override fun getPackage(): String {
        return "rainyspring.silivia"
    }

    override fun onInitialize()
    {
        logger.info("Start Initializing.")

        Balthasar.Init.eventBusInitialize()
        Balthasar.Init.modsInitialize()

        logger.info("Initializing end.")
    }
}
