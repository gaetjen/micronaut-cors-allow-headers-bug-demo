package example.micronaut

import io.micronaut.context.annotation.Factory
import io.micronaut.core.convert.ConversionContext
import io.micronaut.core.convert.TypeConverter
import jakarta.inject.Singleton
import java.util.Optional


@Factory
class MyIntListConverter {
    @Singleton
    fun stringToFooBarListConverter(): TypeConverter<String, FooBarList> {
        println("creating string to foobar list converter")
        return TypeConverter { stringValue, _, conversionContext ->
            println("converting $stringValue")
            println(conversionContext == ConversionContext.LIST_OF_STRING)
            println("conversionContext $conversionContext")
            val fooBar = stringValue?.split(",")
                ?.mapNotNull { str -> FooBar.entries.firstOrNull { it.toString().equals(str.trim(), ignoreCase = true) } }
            Optional.ofNullable(FooBarList(fooBar ?: emptyList()))
        }
    }

    @Singleton
    fun stringToIntListConverter(): TypeConverter<String, IntList> {
        println("creating string to int list converter")
        return TypeConverter { stringValue, _, conversionContext ->
            println("converting $stringValue")
            println(conversionContext == ConversionContext.LIST_OF_STRING)
            println("conversionContext $conversionContext")
            val ints = stringValue?.split(",")?.mapNotNull { it.trim().toIntOrNull() }
            Optional.ofNullable(IntList(ints ?: emptyList()))
        }
    }
}

data class IntList(
    val value: List<Int>
)

data class FooBarList(
    val value: List<FooBar>
)