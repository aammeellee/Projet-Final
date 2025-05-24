package fr.epf.min2.projetfinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter(private var products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.product_image)
        val title: TextView = itemView.findViewById(R.id.product_title)
        val price: TextView = itemView.findViewById(R.id.product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.title.text = product.title
        holder.price.text = "${product.price} €"
        Glide.with(holder.itemView.context).load(product.image).into(holder.image)

        val heartIcon = holder.itemView.findViewById<ImageView>(R.id.favorite_icon)
        heartIcon.setImageResource(
            if (product.isFavorite) R.drawable.ic_red_heart else R.drawable.ic_heart_vide
        )

        heartIcon.setOnClickListener {
            product.isFavorite = !product.isFavorite

            heartIcon.setImageResource(
                if (product.isFavorite) R.drawable.ic_red_heart else R.drawable.ic_heart_vide
            )

            if (product.isFavorite) {
                WishlistManager.add(product)
            } else {
                WishlistManager.remove(product)
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailActivity::class.java)
            intent.putExtra("product_id", product.id)
            holder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = products.size

    fun updateList(newList: List<Product>) {
        products = newList
        notifyDataSetChanged()
    }
}
