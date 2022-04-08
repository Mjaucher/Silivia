package me.meowcher.silivia.utils.world

class URender
{
    companion object
    {
        private var tickCounter : Double = 1.0

        fun setTimer(Value : Double)
        {
            tickCounter = Value
        }
        fun getTimer() : Double
        {
            return tickCounter
        }
    }
}
