package rainyspring.silivia.impl

import rainyspring.silivia.core.Casper
import rainyspring.silivia.core.Melchior
import rainyspring.silivia.events.DoItemUseEvent
import rainyspring.silivia.events.HandleBreakingEvent
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

object MultiTask: Melchior,
    Module(
        Casper.Reference.category,
        "multi-task",
        "Allows you to eat while breaking a block."
) {

    @EventHandler private fun onDoItemUseEvent(event: DoItemUseEvent)
    {
        event.hook = false
    }

    @EventHandler
    private fun onHandleBreakingEvent(event: HandleBreakingEvent)
    {
        event.hook = false
    }
}
