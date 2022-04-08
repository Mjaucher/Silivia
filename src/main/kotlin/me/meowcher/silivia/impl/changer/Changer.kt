package me.meowcher.silivia.impl.changer

import me.meowcher.silivia.core.Global
import me.meowcher.silivia.core.Initializer
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.settings.DoubleSetting
import meteordevelopment.meteorclient.settings.EnumSetting
import meteordevelopment.meteorclient.settings.IntSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler


class Changer : Global, Module(Initializer.Category, "changer", "Enters password after logging in to server.")
{
    private val defaultGroup = settings.defaultGroup
    private var fpsMax = defaultGroup.add(IntSetting.Builder().name("fps-max").defaultValue(120).sliderRange(0, 180).build())
    private var fovValue = defaultGroup.add(DoubleSetting.Builder().name("fov-value").defaultValue(120.0).min(0.0).sliderMax(360.0).build())
    private var gammaValue = defaultGroup.add(DoubleSetting.Builder().name("gamma").defaultValue(666.0).min(0.0).sliderMax(999.0).build())
    private var experienceGroup = settings.createGroup("Experience Bar Settings")
    private var experience = experienceGroup.add(BoolSetting.Builder().name("experience-bar").defaultValue(false).build())
    private var levelNumber = experienceGroup.add(IntSetting.Builder().name("level").defaultValue(99).sliderRange(0, 250).visible{experience.get()}.build())
    private var progressBar = experienceGroup.add(DoubleSetting.Builder().name("progress").defaultValue(1.0).min(0.0).sliderMax(1.0).visible{experience.get()}.build())
    private var weatherGroup = settings.createGroup("Weather Settings")
    private var weather = weatherGroup.add(BoolSetting.Builder().name("weather").defaultValue(false).build())
    private var weatherChoice = weatherGroup.add(EnumSetting.Builder().name("weather-choice").defaultValue(WeatherEnum.Clear).visible{weather.get()}.build())
    private var gradientValue = weatherGroup.add(DoubleSetting.Builder().name("gradient-value").defaultValue(0.0).min(0.0).sliderMax(4.0).visible{weather.get()}.build())

    private var fps = 0
    private var level = 0
    private var fov = 0.0
    private var progress = 0F
    private var gamma = 0.0

    override fun onActivate()
    {
        fov = options.fov
        fps = options.maxFps
        gamma = options.gamma
        level = player!!.experienceLevel
        progress = player!!.experienceProgress
    }

    override fun onDeactivate()
    {
        options.fov = fov
        options.maxFps = fps
        options.gamma = gamma
        player!!.experienceLevel = level
        player!!.experienceProgress = progress
    }

    @EventHandler private fun onTickPostEvent(Event : Post)
    {
        if (options.fov != fovValue.get()) options.fov = fovValue.get()
        if (options.maxFps != fpsMax.get()) options.maxFps = fpsMax.get()
        if (options.gamma != gammaValue.get()) options.gamma = gammaValue.get()

        player!!.setExperience(progressBar.get().toFloat(), 0, levelNumber.get())

        if (weather.get())
        {
            val gradient = if (weatherChoice.get() == WeatherEnum.Rain) gradientValue.get().toFloat() else 0F

            world?.levelProperties?.isRaining = (gradient > 0F)
            world?.setRainGradient(gradient)
            world?.setThunderGradient(0F)
        }
    }

    enum class WeatherEnum
    {
        Rain,
        Clear
    }
}
