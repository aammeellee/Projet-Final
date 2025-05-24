package fr.epf.min2.projetfinal

object WishlistManager {
    private val wishlist = mutableListOf<Product>()

    fun add(product: Product) {
        if (!wishlist.any { it.id == product.id }) wishlist.add(product)
    }

    fun remove(product: Product) {
        wishlist.removeAll { it.id == product.id }
    }

    fun getAll(): List<Product> = wishlist
}
