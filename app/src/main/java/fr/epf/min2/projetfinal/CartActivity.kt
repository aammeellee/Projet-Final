package fr.epf.min2.projetfinal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var totalPriceText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        totalPriceText = findViewById(R.id.totalPriceText)

        val cartItems = CartManager.getCartItems()
        val adapter = ProductAdapter(cartItems)
        cartRecyclerView.adapter = adapter
        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        val total = CartManager.getTotalPrice()
        totalPriceText.text = "Total : %.2f €".format(total)

        val clearButton = findViewById<Button>(R.id.clearCartButton)
        clearButton.setOnClickListener {
            CartManager.clearCart()
            cartRecyclerView.adapter = ProductAdapter(emptyList())
            totalPriceText.text = "Total : 0.00 €"
            Toast.makeText(this, "Panier vidé", Toast.LENGTH_SHORT).show()
        }

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
                true
            }
            R.id.action_scan -> {
                val intent = Intent(this, QRCodeScannerActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_cart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
