package fr.epf.min2.projetfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var productRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productRecyclerView = findViewById(R.id.productRecyclerView)

        lifecycleScope.launch {
            try {
                val products = ApiClient.apiService.getAllProducts()
                productRecyclerView.adapter = ProductAdapter(products)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
