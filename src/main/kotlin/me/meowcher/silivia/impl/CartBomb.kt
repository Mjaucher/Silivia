package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import me.meowcher.silivia.utils.chat.UMessages
import me.meowcher.silivia.utils.player.UInteract
import me.meowcher.silivia.utils.player.UInventory
import me.meowcher.silivia.utils.world.UBlock
import me.meowcher.silivia.utils.world.UEntity
import meteordevelopment.meteorclient.events.game.GameLeftEvent
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.settings.IntSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.text.LiteralText
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction

class CartBomb : Melchior, Module(Casper.Reference.category, "cart-bomb", "Automatically puts Mine Cart with TNT under enemy feet.")
{
    private val group = settings.defaultGroup
    private var targetRange = group.add(IntSetting.Builder().name("target-range").defaultValue(4).sliderRange(1, 6).build())
    private var placementDelay = group.add(IntSetting.Builder().name("placement-delay").defaultValue(5).sliderRange(0, 20).build())
    private var antiSelfDMG = group.add(BoolSetting.Builder().name("anti-self-dmg").defaultValue(true).build())
    private var ignoreFriends = group.add(BoolSetting.Builder().name("ignore-friends").defaultValue(true).build())
    private var onlySurrounded = group.add(BoolSetting.Builder().name("only-surrounded").description("Module is activated when target is surrounded.").defaultValue(true).build())
    private var debugs = group.add(BoolSetting.Builder().name("debugs").defaultValue(false).build())

    private var shutdownGroup = settings.createGroup("Shutdown Settings")
    private var foundResultShutdown = shutdownGroup.add(BoolSetting.Builder().name("found-result-shutdown").defaultValue(true).build())
    private var worldLeftShutdown = shutdownGroup.add(BoolSetting.Builder().name("world-left-shutdown").defaultValue(true).build())
    private var lowHealthStop = shutdownGroup.add(BoolSetting.Builder().name("low-health-stop").defaultValue(true).build())
    private var lowHealthToggle = shutdownGroup.add(BoolSetting.Builder().name("low-health-turn-off").defaultValue(true).visible{lowHealthStop.get()}.build())
    private var lowHealthCount = shutdownGroup.add(IntSetting.Builder().name("minimal-health").defaultValue(5).sliderRange(1, 36).visible{lowHealthStop.get()}.build())

    private var doMineCartPlace = false

    private var ticks = 0
    private var bugTicks = 0

    override fun onActivate()
    {
        doSetDefault()
    }

    override fun onDeactivate()
    {
        doSetDefault()
    }

    private fun getRailSlot() : Int
    {
        var itemSlot = -1
        for (i in 0..8)
        {
            if (isRailItem(UInventory.getItem(i)!!))
            {
                itemSlot = i
                break
            }
        }
        return itemSlot
    }

    @EventHandler private fun onGameLeftEvent(Event : GameLeftEvent)
    {
        if (worldLeftShutdown.get()) toggle()
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        val target = UEntity.getTarget(targetRange.get(), ignoreFriends.get()) ?: return
        val targetPos = BlockPos(target.blockPos)

        val isRailBlock = UBlock.getBlock(targetPos) === Block.getBlockFromItem(UInventory.getItem(getRailSlot()))

        val getCartSlot = UInventory.getItemSlot(Items.TNT_MINECART, false)
        val slot = if (doMineCartPlace) getCartSlot else getRailSlot()

        val notSurrounded = onlySurrounded.get() && !UEntity.isSurrounded(target)
        val isBurrowed = !UBlock.isAir(targetPos) && !isRailBlock
        val antiSelf = antiSelfDMG.get() && (targetPos == player!!.blockPos) && !player!!.isCreative && !player!!.isSpectator
        val lowHPStop = lowHealthStop.get() && player!!.health <= lowHealthCount.get().toFloat()

        if (notSurrounded) doSendDebug("Target is not surrounded!")
        if (isBurrowed) doSendDebug("Target is burrowed, placement is impossible!")
        if (antiSelf || isBurrowed || notSurrounded) return

        if (lowHPStop)
        {
            doSendDebug("You have minimum allowable amount of health, work is stopped!")

            if (lowHealthToggle.get()) toggle()
            else return
        }

        if (slot < 0 || slot > 9)
        {
            val wrongFoundText =
                if (doMineCartPlace) "TNT Cart is not in your hot bar!"
                else "Rails is not in your hot bar!"

            doSendDebug(wrongFoundText)

            if (foundResultShutdown.get()) toggle()
            else return
        }

        if (ticks == placementDelay.get()) UInventory.doSelectSlot(slot)

        if (ticks >= (placementDelay.get() + 1))
        {
            if (isRailBlock == doMineCartPlace && UBlock.isAir(targetPos) == !doMineCartPlace)
            {
                if (UInventory.getCurrentSlot() == slot)
                {
                    UInteract.doUseItemOnBlock(targetPos, Direction.UP, Hand.MAIN_HAND)
                    doMineCartPlace = slot != getCartSlot
                    ticks = 0
                }
            }
        }

        ticks++
    }

    private fun isRailItem(Item : Item) : Boolean
    {
        return Item === Items.RAIL || Item === Items.POWERED_RAIL ||
            Item === Items.ACTIVATOR_RAIL || Item === Items.ACTIVATOR_RAIL
    }

    private fun doSendDebug(Text : String)
    {
        if (debugs.get()) UMessages.doFakeSend(LiteralText(Text))
        else println(Text)
    }

    private fun doSetDefault()
    {
        doMineCartPlace = false
        ticks = 0
        bugTicks = 0
    }
}
