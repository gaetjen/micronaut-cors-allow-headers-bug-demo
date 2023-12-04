package example.micronaut

import io.micronaut.context.annotation.Factory
import io.micronaut.core.convert.TypeConverter
import jakarta.inject.Singleton
import java.util.Optional


@Factory
class MyIntListConverter {
    @Singleton
    fun stringToIntListConverter(): TypeConverter<String, List<Int>> {
        println("creating string to list converter")
        return TypeConverter { stringValue, _, _ ->
            println("converting $stringValue")
            val ints = stringValue?.split(",")?.mapNotNull { it.trim().toIntOrNull() }
            Optional.ofNullable(ints)
        }
    }
}
