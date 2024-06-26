package com.example.fesenko1.Database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fesenko1.R

class ProductAdapter(
    private val products: MutableList<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val tvYear: TextView = itemView.findViewById(R.id.tvYear)
        private val tvManufacturer: TextView = itemView.findViewById(R.id.tvManufacturer)

        fun bind(product: Product) {
            tvQuantity.text = "Quantity: ${product.quantity}"
            tvPrice.text = "Price: ${product.price}"
            tvYear.text = "Year: ${product.year}"
            tvManufacturer.text = "Manufacturer: ${product.manufacturer}"

            itemView.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    fun addProduct(product: Product) {
        products.add(product)
        notifyItemInserted(products.size - 1)
    }

    fun removeProduct(product: Product) {
        val position = products.indexOf(product)
        if (position != -1) {
            products.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateProduct(oldProduct: Product, newProduct: Product) {
        val position = products.indexOf(oldProduct)
        if (position != -1) {
            products[position] = newProduct
            notifyItemChanged(position)
        }
    }

    fun getMostExpensiveProduct(): Product? {
        return products.maxByOrNull { it.price }
    }
}
