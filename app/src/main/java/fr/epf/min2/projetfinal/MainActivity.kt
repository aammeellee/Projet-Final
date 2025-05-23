package fr.epf.min2.projetfinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var productRecyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var searchEditText: EditText
    private var fullProductList: List<Product> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productRecyclerView = findViewById(R.id.productRecyclerView)
        searchEditText = findViewById(R.id.searchEditText)

        lifecycleScope.launch {
            try {
                fullProductList = ApiClient.apiService.getAllProducts()
                adapter = ProductAdapter(fullProductList)
                productRecyclerView.adapter = adapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase()
                val filtered = fullProductList.filter {
                    it.title.lowercase().contains(query)
                }
                adapter.updateList(filtered)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        findViewById<Button>(R.id.viewCartButton).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val scanButton = findViewById<Button>(R.id.scanButton)
        scanButton.setOnClickListener {
            val intent = Intent(this, QRCodeScannerActivity::class.java)
            startActivity(intent)
        }



    }
}

