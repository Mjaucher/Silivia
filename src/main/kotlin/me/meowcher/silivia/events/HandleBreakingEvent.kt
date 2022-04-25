package me.meowcher.silivia.events

class HandleBreakingEvent
{
    var hook = false

    companion object
    {
        private val instance = HandleBreakingEvent()

        fun hook(Hook : Boolean) : HandleBreakingEvent
        {
            instance.hook = Hook
            return instance
        }
    }
}
