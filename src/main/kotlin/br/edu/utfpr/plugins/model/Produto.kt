package br.edu.utfpr.plugins.model

import kotlinx.serialization.Serializable

@Serializable
data class Produto(
    var nome: String,
    var valor: Float,
    var oferta: Oferta?
)

enum class Oferta {
    NORMAL,
    CLUBE
}