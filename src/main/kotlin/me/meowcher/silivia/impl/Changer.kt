package me.meowcher.silivia.impl

import me.meowcher.silivia.core.Melchior
import me.meowcher.silivia.core.Casper
import meteordevelopment.meteorclient.events.world.TickEvent.Post
import meteordevelopment.meteorclient.settings.BoolSetting
import meteordevelopment.meteorclient.settings.DoubleSetting
import meteordevelopment.meteorclient.settings.EnumSetting
import meteordevelopment.meteorclient.settings.IntSetting
import meteordevelopment.meteorclient.systems.modules.Module
import meteordevelopment.orbit.EventHandler

enum class WeatherEnum
{
    Clear,
    Rain
}

class Changer : Module(
    Casper.Reference.category,
    "changer",
    "Changes in-game parameters (EXP Bar, Weather, Fov, FPS limit, and more)."
), Melchior {

    private var mcOptionsGroup = settings.createGroup("Better Minecraft Options")
    private var fpsMax = mcOptionsGroup.add(IntSetting.Builder().name("fps-max").defaultValue(120).sliderRange(0, 180).build())
    private var fovValue = mcOptionsGroup.add(IntSetting.Builder().name("fov-value").defaultValue(120).sliderRange(1, 179).build())
    private var gammaValue = mcOptionsGroup.add(DoubleSetting.Builder().name("gamma").defaultValue(666.0).min(0.0).sliderMax(999.0).build())

    private var experienceGroup = settings.createGroup("Experience Bar Settings")
    private var experience = experienceGroup.add(BoolSetting.Builder().name("experience-bar").defaultValue(false).build())
    private var levelNumber = experienceGroup.add(IntSetting.Builder().name("level").defaultValue(99).sliderRange(0, 250).visible{experience.get()}.build())
    private var progressBar = experienceGroup.add(DoubleSetting.Builder().name("progress").defaultValue(1.0).min(0.0).sliderMax(1.0).visible{experience.get()}.build())

    private var weatherGroup = settings.createGroup("Weather Settings")
    private var weather = weatherGroup.add(BoolSetting.Builder().name("weather").defaultValue(false).build())
    private var weatherChoice = weatherGroup.add(EnumSetting.Builder().name("weather-choice").defaultValue(WeatherEnum.Clear).visible{weather.get()}.build())
    private var gradientValue = weatherGroup.add(DoubleSetting.Builder().name("gradient-value").defaultValue(0.0).min(0.0).sliderMax(1.0).visible{weather.get()}.build())

    private var fps = 0
    private var level = 0
    private var fov = 0
    private var progress = 0F
    private var gamma = 0.0

    override fun onActivate() {

        fov = options.fov.value
        fps = options.maxFps.value
        gamma = options.gamma.value
        level = player.experienceLevel
        progress = player.experienceProgress
    }

    override fun onDeactivate() {

        options.fov.value = fov
        options.maxFps.value = fps
        options.gamma.value = gamma
        player.experienceLevel = level
        player.experienceProgress = progress
    }

    @EventHandler
    private fun onTickPostEvent(Event : Post)
    {
        options.fov.value = fovValue.get()
        options.maxFps.value = fpsMax.get()
        options.gamma.value = gammaValue.get()

        player.setExperience(
            progressBar.get().toFloat(),
            0,
            levelNumber.get()
        )

        if (weather.get()) {

            val gradient =
                if (weatherChoice.get() == WeatherEnum.Rain)
                    gradientValue.get().toFloat()
                else 0F

            world.levelProperties?.isRaining = gradient > 0F
            world.setRainGradient(gradient)
            world.setThunderGradient(0F)
        }
    }
}
