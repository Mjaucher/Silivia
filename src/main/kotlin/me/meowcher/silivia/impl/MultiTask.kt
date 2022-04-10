package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import me.meowcher.silivia.events.BreakingBlock
import me.meowcher.silivia.events.UsingItem
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

class MultiTask : Global, Module(Initializer.Category, "multi-task", "Allows you to eat while breaking a block.")
{
    private var yes = false // deception

    @EventHandler private fun onBreakingBlockEvent(Event : BreakingBlock)
    {
        Event.boo = yes
    }
    @EventHandler private fun onUsingItemEvent(Event : UsingItem)
    {
        Event.lean = yes
    }
}
