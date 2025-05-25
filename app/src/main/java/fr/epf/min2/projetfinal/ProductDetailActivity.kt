package fr.epf.min2.projetfinal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        supportActionBar?.title = "Détail du produit"


        val imageView = findViewById<ImageView>(R.id.product_detail_image)
        val titleView = findViewById<TextView>(R.id.product_detail_title)
        val priceView = findViewById<TextView>(R.id.product_detail_price)
        val descView = findViewById<TextView>(R.id.product_detail_description)

        val productId = intent.getIntExtra("product_id", -1)

        val ratingView = findViewById<TextView>(R.id.product_detail_rating)


        lifecycleScope.launch {
            try {
                val product = ApiClient.apiService.getProductById(productId)
                titleView.text = product.title
                priceView.text = "${product.price} €"
                descView.text = product.description
                Glide.with(this@ProductDetailActivity).load(product.image).into(imageView)
                ratingView.text = "Note : ${product.rating.rate} ⭐ (${product.rating.count} avis)"


                val addButton = findViewById<Button>(R.id.addToCartButton)
                addButton.setOnClickListener {
                    CartManager.addToCart(product)
                    Toast.makeText(
                        this@ProductDetailActivity,
                        "Ajouté au panier",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                val favIcon = findViewById<ImageView>(R.id.detail_favorite_icon)

                favIcon.setImageResource(
                    if (product.isFavorite) R.drawable.ic_red_heart else R.drawable.ic_heart_vide
                )

                favIcon.setOnClickListener {
                    product.isFavorite = !product.isFavorite

                    favIcon.setImageResource(
                        if (product.isFavorite) R.drawable.ic_red_heart else R.drawable.ic_heart_vide
                    )

                    if (product.isFavorite) {
                        WishlistManager.add(product)
                        Toast.makeText(this@ProductDetailActivity, "Ajouté aux favoris", Toast.LENGTH_SHORT).show()
                    } else {
                        WishlistManager.remove(product)
                        Toast.makeText(this@ProductDetailActivity, "Retiré des favoris", Toast.LENGTH_SHORT).show()
                    }
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

