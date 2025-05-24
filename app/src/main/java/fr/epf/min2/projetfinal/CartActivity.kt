package fr.epf.min2.projetfinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalPriceText: TextView
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Mon panier"

        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        totalPriceText = findViewById(R.id.totalPriceText)

        adapter = CartAdapter(CartManager.getCartItems()) {
            updateTotal()
            if (CartManager.getCartItems().isEmpty()) {
                Toast.makeText(this, "Panier vide", Toast.LENGTH_SHORT).show()
            }
        }

        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = adapter

        updateTotal()

        val clearButton = findViewById<Button>(R.id.clearCartButton)
        clearButton.setOnClickListener {
            CartManager.clearCart()
            adapter.updateList(emptyList())
            updateTotal()
            Toast.makeText(this, "Panier vidé", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTotal() {
        val total = CartManager.getTotalPrice()
        totalPriceText.text = "Total : %.2f €".format(total)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_wishlist -> {
                startActivity(Intent(this, WishlistActivity::class.java))
                return true
            }
            R.id.action_scan -> {
                startActivity(Intent(this, QRCodeScannerActivity::class.java))
                return true
            }
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
