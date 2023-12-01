package example.micronaut

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.bind.ArgumentBinder
import io.micronaut.core.convert.ArgumentConversionContext
import io.micronaut.core.convert.TypeConverter
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.bind.binders.RequestArgumentBinder
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder
import jakarta.inject.Singleton
import java.util.Optional

@Introspected
enum class FooBar {
    FOO, BAR
}

@Requires(classes = [RequestArgumentBinder::class])
@Singleton
class FooBarRequestArgumentBinder(
    private val myConverter: TypeConverter<String, FooBar>,
) : TypedRequestArgumentBinder<FooBar> {

    override fun bind(
        context: ArgumentConversionContext<FooBar>,
        source: HttpRequest<*>
    ): ArgumentBinder.BindingResult<FooBar> { //(1)

        val stringValue = source.parameters?.get("fooBar")

        return if (stringValue == null)
            ArgumentBinder.BindingResult {
                Optional.empty()
            }
        else {
            ArgumentBinder.BindingResult {
                myConverter.convert(stringValue, FooBar::class.java)
            }
        }
    }

    override fun argumentType(): Argument<FooBar> {
        return Argument.of(FooBar::class.java) //(3)
    }
}

@Factory
class TypeConverterFactory {
    @Singleton
    fun myConverter(): TypeConverter<String, FooBar> {
        return TypeConverter { stringValue, _, _ ->
            when (stringValue) {
                "foo" -> Optional.of(FooBar.FOO)
                "bar" -> Optional.of(FooBar.BAR)
                else -> Optional.empty()
            }
        }
    }

    @Singleton
    fun mySerializer(): TypeConverter<FooBar, String> {
        return TypeConverter { fooBar, _, _ ->
            Optional.ofNullable(fooBar?.name?.lowercase())
        }
    }
}
