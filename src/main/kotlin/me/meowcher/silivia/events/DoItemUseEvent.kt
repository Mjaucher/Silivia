package me.meowcher.silivia.events

class DoItemUseEvent
{
    var hook = false

    companion object
    {
        private val instance = DoItemUseEvent()

        fun hook(Hook : Boolean) : DoItemUseEvent
        {
            instance.hook = Hook
            return instance
        }
    }
}
