package com.example.projetofinal.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projetofinal.data.Produto
import com.example.projetofinal.data.ProdutoRepository
import com.example.projetofinal.databinding.ActivityAddEditProdutoBinding
import com.example.projetofinal.data.AppDatabase

class AddEditProdutoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditProdutoBinding
    private val viewModel: ProdutoViewModel by viewModels {
        ProdutoViewModelFactory(
            ProdutoRepository(
                AppDatabase.getDatabase(this).produtoDao()
            )
        )
    }

    private var produtoId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        produtoId = intent.getIntExtra("PRODUTO_ID", -1)

        if (produtoId != -1) {
            title = "Editar Produto"
            viewModel.getProduto(produtoId).observe(this) { produto ->
                produto?.let {
                    binding.editNome.setText(it.nome)
                    binding.editPreco.setText(it.preco.toString())
                    binding.editQuantidade.setText(it.quantidade.toString())
                }
            }
        } else {
            title = "Adicionar Produto"
        }

        binding.btnSalvar.setOnClickListener {
            salvarProduto()
        }
    }

    private fun salvarProduto() {
        val nome = binding.editNome.text.toString()
        val preco = binding.editPreco.text.toString().toDoubleOrNull() ?: 0.0
        val quantidade = binding.editQuantidade.text.toString().toIntOrNull() ?: 0

        if (nome.isBlank()) {
            binding.editNome.error = "Nome não pode estar vazio"
            return
        }

        val produto = Produto(
            id = if (produtoId != -1) produtoId else 0,
            nome = nome,
            preco = preco,
            quantidade = quantidade
        )

        if (produtoId != -1) {
            viewModel.update(produto)
        } else {
            viewModel.insert(produto)
        }

        finish()
    }
}