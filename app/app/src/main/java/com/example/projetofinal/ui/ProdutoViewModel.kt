package com.example.projetofinal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projetofinal.data.Produto
import com.example.projetofinal.data.ProdutoRepository
import kotlinx.coroutines.launch

class ProdutoViewModel(private val repository: ProdutoRepository) : ViewModel() {
    val allProdutos = repository.allProdutos

    fun insert(produto: Produto) = viewModelScope.launch {
        repository.insert(produto)
    }

    fun update(produto: Produto) = viewModelScope.launch {
        repository.update(produto)
    }

    fun delete(produto: Produto) = viewModelScope.launch {
        repository.delete(produto)
    }

    fun getProduto(id: Int) = repository.getProduto(id)
}

class ProdutoViewModelFactory(private val repository: ProdutoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProdutoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProdutoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}