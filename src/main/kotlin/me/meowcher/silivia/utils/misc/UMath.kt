package me.meowcher.silivia.utils.misc


class UMath
{
    companion object
    {
        fun isEvenNumber(Number : Int) : Boolean
        {
            return Number % 2 == 0
        }

        fun getTicksPerSecond() : Double
        {
            var maxTicksPerSecond = 0.0
            var ticks = 0.0

            for (array in DoubleArray(20))
            {
                if (array > 0.0)
                {
                    maxTicksPerSecond += array
                    ticks += 1.0
                }
            }

            val quotient = maxTicksPerSecond / ticks

            return if (quotient < 0.0) 0.0 else (if (quotient > 20.0) 20.0 else quotient)
        }
    }
}
