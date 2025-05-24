package fr.epf.min2.projetfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CartAdapter(
    private var items: List<Product>,
    private val onItemRemoved: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.cart_item_title)
        val price: TextView = view.findViewById(R.id.cart_item_price)
        val image: ImageView = view.findViewById(R.id.cart_item_image)
        val deleteIcon: ImageView = view.findViewById(R.id.delete_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = items[position]
        holder.title.text = product.title
        holder.price.text = "%.2f €".format(product.price)

        Glide.with(holder.itemView.context)
            .load(product.image)
            .into(holder.image)

        holder.deleteIcon.setOnClickListener {
            CartManager.removeProduct(product)
            updateList(CartManager.getCartItems())
            onItemRemoved()
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<Product>) {
        items = newList
        notifyDataSetChanged()
    }
}
