package com.example.projetofinal.data

import androidx.lifecycle.LiveData

class ProdutoRepository(private val produtoDao: ProdutoDao) {
    val allProdutos: LiveData<List<Produto>> = produtoDao.getAllProdutos()

    suspend fun insert(produto: Produto) {
        produtoDao.insert(produto)
    }

    suspend fun update(produto: Produto) {
        produtoDao.update(produto)
    }

    suspend fun delete(produto: Produto) {
        produtoDao.delete(produto)
    }

    fun getProduto(id: Int): LiveData<Produto> {
        return produtoDao.getProdutoById(id)
    }
}