package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Casper
import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.events.DoItemUseEvent
import me.meowcher.silivia.events.HandleBreakingEvent
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

class MultiTask : Melchior, Module(Casper.Reference.category, "multi-task", "Allows you to eat while breaking a block.")
{
    @EventHandler private fun onDoItemUseEvent(Event : DoItemUseEvent)
    {
        Event.hook = false
    }

    @EventHandler
    private fun onHandleBreakingEvent(Event : HandleBreakingEvent)
    {
        Event.hook = false //yes usu itme!!
    }
}
