package fr.epf.min2.projetfinal

import android.os.Bundle
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
        supportActionBar?.title = "Mon panier"

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
}
