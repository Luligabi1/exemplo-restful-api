package br.edu.utfpr.plugins

import br.edu.utfpr.plugins.model.Oferta
import br.edu.utfpr.plugins.model.Produto
import br.edu.utfpr.plugins.model.ProdutoRepositorio
import io.ktor.http.*
import io.ktor.serialization.JsonConvertException
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("/ui-produto", "ui-produto")

        get("/") {
            call.respondText("Hello World!")
        }
        get("/produto/") {
            val produtos = ProdutoRepositorio.todos()
            call.respond(produtos)
        }
        get("/produto/oferta/{oferta}") {
            getProdutoByOferta(call, call.parameters["oferta"])
        }
        get("/produto/oferta/") {
            getProdutoByOferta(call, null)
        }

        post("/produto") {
            try {
                val produto = call.receive<Produto>()
                ProdutoRepositorio.adicionar(produto)
                call.respond(HttpStatusCode.NoContent)
            } catch (_: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (_: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        put("/produto") {
            try {
                val produto = call.receive<Produto>()
                ProdutoRepositorio.atualizar(produto)
                call.respond(HttpStatusCode.NoContent)
            } catch (_: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (_: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        delete("/produto/{nome}") {
            try {
                val nome = call.parameters["nome"]
                ProdutoRepositorio.remover(nome)
                call.respond(HttpStatusCode.NoContent)
            } catch (_: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (_: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }


}

private suspend fun getProdutoByOferta(call: RoutingCall, input: String?) {
    try {
        val oferta = Oferta.entries.firstOrNull { it.name.equals(input, ignoreCase = true) }
        val produtos = ProdutoRepositorio.oferta(oferta)

        if (produtos.isEmpty()) {
            call.respond(HttpStatusCode.NotFound)
            return
        }

        call.respond(produtos)
    } catch(_: IllegalArgumentException) {
        call.respond(HttpStatusCode.BadRequest)
    }
}