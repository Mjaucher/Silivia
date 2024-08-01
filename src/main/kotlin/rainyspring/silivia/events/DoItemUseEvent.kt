package rainyspring.silivia.events

class DoItemUseEvent
{
    var hook = false

    companion object
    {
        private val instance = DoItemUseEvent()

        fun hook(hook: Boolean): DoItemUseEvent
        {
            instance.hook = hook
            return instance
        }
    }
}
