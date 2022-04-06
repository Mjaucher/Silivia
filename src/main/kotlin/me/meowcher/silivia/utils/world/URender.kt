package me.meowcher.silivia.utils.world

class URender
{
    companion object
    {
        private var tickCounter : Double = 1.0

        fun setTick(Value : Double)
        {
            tickCounter = Value
        }
        fun getTick() : Double
        {
            return tickCounter
        }
    }
}
