package rainyspring.silivia.utils.misc

object UVariables {

    fun repeat(
        string: String,
        repeatCount: Int,
        withSpace: Boolean
    ): String {

        var result = ""

        IntRange(1, repeatCount).forEach {
            result += string +
                if (it != repeatCount && withSpace) " "
                else ""
        }

        return result
    }
}
