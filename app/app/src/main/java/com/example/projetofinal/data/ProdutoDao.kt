package com.example.projetofinal.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProdutoDao {
    @Insert
    suspend fun insert(produto: Produto)

    @Update
    suspend fun update(produto: Produto)

    @Delete
    suspend fun delete(produto: Produto)

    @Query("SELECT * FROM produtos ORDER BY nome ASC")
    fun getAllProdutos(): LiveData<List<Produto>>

    @Query("SELECT * FROM produtos WHERE id = :produtoId")
    fun getProdutoById(produtoId: Int): LiveData<Produto>
}