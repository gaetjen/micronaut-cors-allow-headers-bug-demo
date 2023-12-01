package example.micronaut

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue

@Controller("/hello") // <1>
class HelloController {

    @Get// <2>
    @Produces(MediaType.TEXT_PLAIN) // <3>
    fun index() = "Hello World"

    @Get("/typed")
    fun foo(
        fooBar: FooBar?
    ): HttpResponse<*> { //(1)
        return HttpResponse.ok(
            mapOf(
                "fooBar" to fooBar
            )
        )
    }
}
