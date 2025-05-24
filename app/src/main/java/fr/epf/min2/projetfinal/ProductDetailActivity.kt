package fr.epf.min2.projetfinal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var priceView: TextView
    private lateinit var descView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageView = findViewById<ImageView>(R.id.product_detail_image)
        val titleView = findViewById<TextView>(R.id.product_detail_title)
        val priceView = findViewById<TextView>(R.id.product_detail_price)
        val descView = findViewById<TextView>(R.id.product_detail_description)
        val viewCartButton = findViewById<Button>(R.id.viewCartButton)

        viewCartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val productId = intent.getIntExtra("product_id", -1)

        lifecycleScope.launch {
            try {
                val product = ApiClient.apiService.getProductById(productId)
                titleView.text = product.title
                priceView.text = "${product.price} €"
                descView.text = product.description
                Glide.with(this@ProductDetailActivity).load(product.image).into(imageView)

                val addButton = findViewById<Button>(R.id.addToCartButton)
                addButton.setOnClickListener {
                    CartManager.addToCart(product)
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Ajouté au panier",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ProductDetailActivity,
                    "Erreur chargement produit",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

}

