package fr.epf.min2.projetfinal

object CartManager {
    private val cartItems = mutableListOf<Product>()

    fun addToCart(product: Product) {
        cartItems.add(product)
    }

    fun getCartItems(): List<Product> = cartItems

    fun getTotalPrice(): Double = cartItems.sumOf { it.price }

    fun clearCart() {
        cartItems.clear()
    }
}
