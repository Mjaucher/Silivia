package me.meowcher.silivia.core

import me.meowcher.silivia.impl.autokit.AutoKit
import me.meowcher.silivia.impl.autologin.AutoLogin
import me.meowcher.silivia.impl.fakekick.FakeKick
import me.meowcher.silivia.impl.killeffects.KillEffects
import me.meowcher.silivia.impl.multitask.MultiTask
import me.meowcher.silivia.impl.nightmode.NightMode
import me.meowcher.silivia.impl.nointeract.NoInteract
import me.meowcher.silivia.impl.noswing.NoSwing
import me.meowcher.silivia.impl.prefix.Prefix
import me.meowcher.silivia.impl.tickshift.TickShift
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.meteorclient.systems.modules.Modules

class Implements
{
    companion object
    {
        private fun modInitialize(vararg Features : Module?)
        {
            for (modules in Features)
            {
                Modules.get().add(modules)
            }
        }
        fun modules()
        {
            modInitialize(KillEffects(), NoInteract(), NightMode(), AutoLogin(), NoSwing(),
                MultiTask(), TickShift(), FakeKick(), AutoKit(), Prefix(),
            )
        }
    }
}