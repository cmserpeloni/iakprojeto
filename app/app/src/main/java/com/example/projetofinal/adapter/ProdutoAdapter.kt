package com.example.projetofinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projetofinal.data.Produto
import com.example.projetofinal.databinding.ItemProdutoBinding

class ProdutoAdapter(
    private val onItemClick: (Produto) -> Unit
) : ListAdapter<Produto, ProdutoAdapter.ProdutoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val binding = ItemProdutoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProdutoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ProdutoViewHolder(private val binding: ItemProdutoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val produto = getItem(position)
                    onItemClick(produto)
                }
            }
        }

        fun bind(produto: Produto) {
            binding.apply {
                textNome.text = produto.nome
                textPreco.text = "Preço: R$ %.2f".format(produto.preco)
                textQuantidade.text = "Quantidade: ${produto.quantidade}"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Produto>() {
        override fun areItemsTheSame(oldItem: Produto, newItem: Produto) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Produto, newItem: Produto) =
            oldItem == newItem
    }
}