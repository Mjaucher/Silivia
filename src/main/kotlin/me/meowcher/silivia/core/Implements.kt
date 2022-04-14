package me.meowcher.silivia.core

import me.meowcher.silivia.impl.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.systems.modules.Modules

class Implements
{
    companion object
    {
        private fun modsInitialize(vararg Features : Module?)
        {
            for (modules in Features)
            {
                Modules.get().add(modules)
            }
        }
        fun modsInitialize()
        {
            modsInitialize(
                KillEffects(),
                NoInteract(),
                NightMode(),
                AutoLogin(),
                MultiTask(),
                TickShift(),
                FakeKick(),
                NoSwing(),
                Changer(),
                AutoKit(),
                Prefix(),
            )
        }
    }
}
