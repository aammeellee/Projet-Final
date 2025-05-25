package fr.epf.min2.projetfinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
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
        val spinner = findViewById<Spinner>(R.id.categorySpinner)

        lifecycleScope.launch {
            try {
                fullProductList = ApiClient.apiService.getAllProducts()
                adapter = ProductAdapter(fullProductList)
                productRecyclerView.adapter = adapter

                val categoryTranslations = mapOf(
                    "men's clothing" to "Vêtements homme",
                    "women's clothing" to "Vêtements femme",
                    "jewelery" to "Bijoux",
                    "electronics" to "Électronique"
                )

                val apiCategories = fullProductList.map { it.category }.distinct()
                val categories = listOf("Tous") + apiCategories.map { categoryTranslations[it] ?: it }

                val spinnerAdapter = ArrayAdapter(
                    this@MainActivity,
                    android.R.layout.simple_spinner_item,
                    categories
                )
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = spinnerAdapter

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val selectedLabel = categories[position]
                        val selected = if (selectedLabel == "Tous") null
                        else categoryTranslations.entries.firstOrNull { it.value == selectedLabel }?.key

                        val filtered = if (selected == null) {
                            fullProductList
                        } else {
                            fullProductList.filter { it.category == selected }
                        }
                        adapter.updateList(filtered)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

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

    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            adapter.notifyDataSetChanged()
        }
    }
}
