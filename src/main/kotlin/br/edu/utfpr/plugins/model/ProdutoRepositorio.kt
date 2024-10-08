package br.edu.utfpr.plugins.model

object ProdutoRepositorio {
    val produtos = mutableListOf(
        Produto("Pão de Forma", 7.49F, null),
        Produto("Carne", 39.99F, Oferta.NORMAL),
        Produto("Sabonete", 3.99F, Oferta.NORMAL),
        Produto("Shampoo", 26.99F, Oferta.CLUBE),
        Produto("Desinfetante", 19.99F, Oferta.CLUBE),
        Produto("Assinatura Clube", 39.95F, null)
    )

    fun todos(): List<Produto> = produtos

    fun oferta(oferta: Oferta?): List<Produto> {
        return produtos.filter {
            if(oferta != null) it.oferta == oferta else it.oferta != null
        }
    }

    fun nome(nome: String) = produtos.find {
        it.nome.equals(nome, ignoreCase = true)
    }

    fun adicionar(produto: Produto) {
        if(nome(produto.nome) != null) {
            throw IllegalStateException("Produto ${produto.nome} já existe!")
        }
        produtos.add(produto)
    }

    fun atualizar(produto: Produto) {
        val produtoAtual = nome(produto.nome)
        if(produtoAtual == null) {
            throw IllegalStateException("Não existe um produto '${produto.nome}' para atualizar!")
        }
        produtoAtual.apply {
            nome = produto.nome
            valor = produto.valor
            oferta = produto.oferta
        }
    }

    fun remover(nome: String?) {
        val produtoRemovido = produtos.find { it.nome == nome }
        if(produtoRemovido == null) {
            throw IllegalStateException("Não existe um produto '$nome' para remover!")
        }

        produtos.remove(produtoRemovido)
    }


}