package com.example.fesenko1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fesenko1.Database.Product
import com.example.fesenko1.Database.ProductAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val products = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        productAdapter = ProductAdapter(products, ::showProductDialog)
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnAddProduct).setOnClickListener {
            showAddProductDialog()
        }

        findViewById<Button>(R.id.btnMostExpensiveProduct).setOnClickListener {
            showMostExpensiveProduct()
        }
    }

    private fun showAddProductDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_product, null)
        val quantityEditText = dialogView.findViewById<EditText>(R.id.etQuantity)
        val priceEditText = dialogView.findViewById<EditText>(R.id.etPrice)
        val yearEditText = dialogView.findViewById<EditText>(R.id.etYear)
        val manufacturerEditText = dialogView.findViewById<EditText>(R.id.etManufacturer)

        AlertDialog.Builder(this)
            .setTitle("Add Product")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val quantity = quantityEditText.text.toString().toInt()
                val price = priceEditText.text.toString().toDouble()
                val year = yearEditText.text.toString().toInt()
                val manufacturer = manufacturerEditText.text.toString()
                val product = Product(quantity, price, year, manufacturer)
                productAdapter.addProduct(product)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun showProductDialog(product: Product) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_product, null)
        val quantityEditText = dialogView.findViewById<EditText>(R.id.etQuantity)
        val priceEditText = dialogView.findViewById<EditText>(R.id.etPrice)
        val yearEditText = dialogView.findViewById<EditText>(R.id.etYear)
        val manufacturerEditText = dialogView.findViewById<EditText>(R.id.etManufacturer)

        quantityEditText.setText(product.quantity.toString())
        priceEditText.setText(product.price.toString())
        yearEditText.setText(product.year.toString())
        manufacturerEditText.setText(product.manufacturer)

        AlertDialog.Builder(this)
            .setTitle("Edit Product")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newProduct = Product(
                    quantityEditText.text.toString().toInt(),
                    priceEditText.text.toString().toDouble(),
                    yearEditText.text.toString().toInt(),
                    manufacturerEditText.text.toString()
                )
                productAdapter.updateProduct(product, newProduct)
            }
            .setNegativeButton("Delete") { _, _ ->
                productAdapter.removeProduct(product)
            }
            .setNeutralButton("Cancel", null)
            .create()
            .show()
    }

    private fun showMostExpensiveProduct() {
        val mostExpensiveProduct = productAdapter.getMostExpensiveProduct()
        if (mostExpensiveProduct != null) {
            AlertDialog.Builder(this)
                .setTitle("Most Expensive Product")
                .setMessage("Quantity: ${mostExpensiveProduct.quantity}\n" +
                        "Price: ${mostExpensiveProduct.price}\n" +
                        "Year: ${mostExpensiveProduct.year}\n" +
                        "Manufacturer: ${mostExpensiveProduct.manufacturer}")
                .setPositiveButton("OK", null)
                .create()
                .show()
        } else {
            AlertDialog.Builder(this)
                .setTitle("No Products")
                .setMessage("There are no products in the list.")
                .setPositiveButton("OK", null)
                .create()
                .show()
        }
    }
}
