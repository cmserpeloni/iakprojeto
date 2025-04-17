package com.example.projetofinal.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.projetofinal.data.AppDatabase
import com.example.projetofinal.data.ProdutoRepository
import com.example.projetofinal.databinding.ActivityProdutoDetailBinding

class ProdutoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProdutoDetailBinding
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
        binding = ActivityProdutoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        produtoId = intent.getIntExtra("PRODUTO_ID", -1)

        if (produtoId == -1) finish()

        viewModel.getProduto(produtoId).observe(this) { produto ->
            produto?.let {
                binding.textNome.text = it.nome
                binding.textPreco.text = "Preço: R$ %.2f".format(it.preco)
                binding.textQuantidade.text = "Quantidade: ${it.quantidade}"
            }
        }

        binding.btnEditar.setOnClickListener {
            val intent = Intent(this, AddEditProdutoActivity::class.java).apply {
                putExtra("PRODUTO_ID", produtoId)
            }
            startActivity(intent)
        }

        binding.btnExcluir.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirmar exclusão")
                .setMessage("Tem certeza que deseja excluir este produto?")
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.getProduto(produtoId).observe(this) { produto ->
                        produto?.let { viewModel.delete(it) }
                        finish()
                    }
                }
                .setNegativeButton("Não", null)
                .show()
        }
    }
}