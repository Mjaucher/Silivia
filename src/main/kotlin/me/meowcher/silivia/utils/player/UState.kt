package me.meowcher.silivia.utils.player

import me.meowcher.silivia.core.Melchior

class UState
{
    companion object : Melchior
    {
        val isDead = player!!.health == 0F || player!!.isDead
        val onLadder = player!!.isHoldingOntoLadder
        val onFire = player!!.isOnFire
        val onWater = player!!.isTouchingWater
        val onGround = player!!.isOnGround
        val onJump = options.jumpKey.isPressed || player!!.input.jumping

        val hCollision = player!!.horizontalCollision
        val vCollision = player!!.verticalCollision
    }
}
