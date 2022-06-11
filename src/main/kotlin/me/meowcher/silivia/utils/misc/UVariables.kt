package me.meowcher.silivia.utils.misc

class UVariables
{
    companion object
    {
        fun repeat(string : String, repeatCount : Int, withSpace : Boolean) : String
        {
            var result = ""

            for (repeats in 1..repeatCount)
            {
                result += string +
                    if (repeats != repeatCount && withSpace) " "
                    else ""
            }

            return result
        }
    }
}
