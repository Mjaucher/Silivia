package silivia.impl

import silivia.Silivia
import silivia.events.BreakingBlock
import silivia.events.UsingItem
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

class MultiTask : Module(Silivia.Special.Category, "Multi-Task", "Allows you to eat while breaking a block.")
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
