package com.example.projetofinal.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projetofinal.adapter.ProdutoAdapter
import com.example.projetofinal.data.AppDatabase
import com.example.projetofinal.data.ProdutoRepository
import com.example.projetofinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProdutoAdapter

    private val viewModel: ProdutoViewModel by viewModels {
        ProdutoViewModelFactory(
            ProdutoRepository(
                AppDatabase.getDatabase(this).produtoDao()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupFAB()
        observeProdutos()
    }

    private fun setupRecyclerView() {
        adapter = ProdutoAdapter { produto ->
            val intent = Intent(this, ProdutoDetailActivity::class.java).apply {
                putExtra("PRODUTO_ID", produto.id)
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupFAB() {
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddEditProdutoActivity::class.java))
        }
    }

    private fun observeProdutos() {
        viewModel.allProdutos.observe(this) { produtos ->
            produtos?.let { adapter.submitList(it) }
        }
    }
}