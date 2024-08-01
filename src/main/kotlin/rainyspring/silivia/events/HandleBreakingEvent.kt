package rainyspring.silivia.events

class HandleBreakingEvent
{
    var hook = false

    companion object
    {
        private val instance = HandleBreakingEvent()

        fun hook(hook: Boolean): HandleBreakingEvent
        {
            instance.hook = hook
            return instance
        }
    }
}
