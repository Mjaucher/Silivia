package me.meowcher.silivia.impl.multitask

import me.meowcher.silivia.utils.addon.Initializer
import me.meowcher.silivia.events.BreakingBlock
import me.meowcher.silivia.events.UsingItem
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

class MultiTask : Module(Initializer.Category, "multi-task", "Allows you to eat while breaking a block.")
{
    private var no = false // =^)

    @EventHandler private fun onEventA(Event : BreakingBlock)
    {
        Event.boo = no
    }
    @EventHandler private fun onEventB(Event : UsingItem)
    {
        Event.lean = no
    }
}
