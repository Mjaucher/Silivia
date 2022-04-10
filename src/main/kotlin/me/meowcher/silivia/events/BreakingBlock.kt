package me.meowcher.silivia.events

class BreakingBlock
{
    var boo = false

    companion object
    {
        private val INSTANCE : BreakingBlock = BreakingBlock()
        fun hook(hook : Boolean) : BreakingBlock
        {
            INSTANCE.boo = hook
            return INSTANCE
        }
    }
}
