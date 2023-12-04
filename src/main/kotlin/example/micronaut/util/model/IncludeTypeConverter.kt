package example.micronaut.util.model

import io.micronaut.context.annotation.Factory
import io.micronaut.core.convert.TypeConverter
import jakarta.inject.Singleton
import java.util.Optional

@Factory
class IncludeTypeConverter {
    @Singleton
    fun includeTypeListConverter(): TypeConverter<List<IncludeType>, String> {
        return TypeConverter { includeTypes, _, _ ->
            val validIncludeTypes = includeTypes?.filter {
                it.name.lowercase() in IncludeType.entries.map { entry -> entry.name.lowercase() }
            }
            Optional.ofNullable(validIncludeTypes?.joinToString(",") { it.stringValue })
        }
    }

    @Singleton
    fun stringToIncludeTypeListConverter(): TypeConverter<String, List<IncludeType>> {
        return TypeConverter { stringValue, _, _ ->
            val types = stringValue?.split(",")?.map { it.trim().lowercase() }
            val includeTypes = types?.mapNotNull { includeTypeString ->
                IncludeType.entries.firstOrNull { it.name.equals(includeTypeString, true) }
            }
            Optional.ofNullable(includeTypes)
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun TypeConverter<String, List<IncludeType>>.convertIncludeTypeList(stringValue: String?): Optional<List<IncludeType>> =
    convert(stringValue, List::class.java as Class<List<IncludeType>>)
