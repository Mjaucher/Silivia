package me.meowcher.silivia.impl.surround

import me.meowcher.silivia.utils.addon.Initializer
import me.meowcher.silivia.utils.player.Movement
import me.meowcher.silivia.utils.player.Interact
import me.meowcher.silivia.utils.misc.Minecraft.Companion.minecraft
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.*
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler
import net.minecraft.util.math.BlockPos
import net.minecraft.item.Items

class Surround : Module(Initializer.Category, "my-surround", "")
{
    private val group = settings.defaultGroup
    private var placementTicks = group.add(IntSetting.Builder().name("placement-ticks").defaultValue(5).sliderRange(0, 15).build())
    private var alwaysInCenter = group.add(BoolSetting.Builder().name("always-in-center").defaultValue(false).build())
    private var ticks = 0

    override fun onActivate()
    {
        Movement.centerTP()
        ticks = 0
    }
    @EventHandler private fun onEventA(Event : Post)
    {
        if (alwaysInCenter.get()) Movement.centerTP()
        val oldYPos = minecraft.player!!.y

        if (ticks >= placementTicks.get())
        {
            place()
            ticks = 0
        } else ticks++

        if (minecraft.player!!.y > oldYPos) toggle()
    }

    private fun place()
    {
        Interact.place(Items.OBSIDIAN, BlockPos(minecraft.player!!.blockX+1, minecraft.player!!.blockY, minecraft.player!!.blockZ), true)
        Interact.place(Items.OBSIDIAN, BlockPos(minecraft.player!!.blockX-1, minecraft.player!!.blockY, minecraft.player!!.blockZ), true)
        Interact.place(Items.OBSIDIAN, BlockPos(minecraft.player!!.blockX, minecraft.player!!.blockY, minecraft.player!!.blockZ+1), true)
        Interact.place(Items.OBSIDIAN, BlockPos(minecraft.player!!.blockX, minecraft.player!!.blockY, minecraft.player!!.blockZ-1), true)
    }
}
