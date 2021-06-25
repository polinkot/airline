import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

plugins {
    kotlin("jvm") version Global.kotlin apply false
}

/**
 * Project configuration by properties and environment
 */
fun envConfig() = object : ReadOnlyProperty<Any?, String?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): String? =
            if (ext.has(property.name)) {
                ext[property.name] as? String
            } else {
                System.getenv(property.name)
            }
}

subprojects {
    group = "com.example.airline"
}
