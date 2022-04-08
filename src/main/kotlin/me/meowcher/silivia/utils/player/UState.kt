package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Global

class UState
{
    companion object : Global
    {
        val isDead = player!!.health == 0F || player!!.isDead
        val onLadder = player!!.isHoldingOntoLadder
        val onFire = player!!.isOnFire
        val onWater = player!!.isTouchingWater
    }
}
