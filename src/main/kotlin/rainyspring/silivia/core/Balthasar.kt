package rainyspring.silivia.core

import rainyspring.silivia.impl.*
import meteordevelopment.meteorclient.MeteorClient
import meteordevelopment.meteorclient.systems.modules.Modules
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method

class Balthasar
{
    object Init: Melchior
    {
        fun modsInitialize() {

            arrayOf(
                AutoLogin,
                NightMode,
                AutoKit,
                DiscordActivity,
                Prefix,
                FakeKick,
                NoSwing,
                MultiTask,
                KillEffects,
                NoInteract,
                TickShift
            ).forEach {
                Modules.get().add(it)
            }

            logger.info("Registration of modules is completed!")
        }

        fun eventBusInitialize() {

            MeteorClient.EVENT_BUS.registerLambdaFactory("rainyspring.silivia") {
                    lookupInMethod: Method, klass: Class<*>? ->
                lookupInMethod.invoke(null, klass, MethodHandles.lookup()) as MethodHandles.Lookup
            }

            logger.info("Event Bus registration is completed!")
        }
    }
}
