package example.micronaut.util.binder

import example.micronaut.util.model.IncludeType
import example.micronaut.util.model.convertIncludeTypeList
import io.micronaut.context.annotation.Requires
import io.micronaut.core.bind.ArgumentBinder
import io.micronaut.core.convert.ArgumentConversionContext
import io.micronaut.core.convert.TypeConverter
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.bind.binders.RequestArgumentBinder
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder
import jakarta.inject.Singleton
import java.util.Optional

@Requires(classes = [RequestArgumentBinder::class])
@Singleton
class IncludeTypeBinder(
    private val converter: TypeConverter<String, List<IncludeType>>,
) : TypedRequestArgumentBinder<List<IncludeType>> {

    override fun bind(
        context: ArgumentConversionContext<List<IncludeType>>?,
        source: HttpRequest<*>?,
    ): ArgumentBinder.BindingResult<List<IncludeType>> {
        val stringValue = source?.parameters?.get("include")

        if (stringValue.isNullOrBlank()) {
            return ArgumentBinder.BindingResult {
                Optional.ofNullable(null)
            }
        }

        val convertedValue = stringValue.let {
            converter.convertIncludeTypeList(it)
        }

        return ArgumentBinder.BindingResult {
            convertedValue
        }
    }

    override fun argumentType(): Argument<List<IncludeType>> {
        return Argument.listOf(IncludeType::class.java)
    }
}
