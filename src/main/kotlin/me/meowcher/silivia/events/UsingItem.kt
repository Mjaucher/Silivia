package me.meowcher.silivia.events

class UsingItem
{
    var lean = false

    companion object
    {
        private val INSTANCE : UsingItem = UsingItem()
        fun hook(hook : Boolean) : UsingItem
        {
            INSTANCE.lean = hook
            return INSTANCE
        }
    }
}
