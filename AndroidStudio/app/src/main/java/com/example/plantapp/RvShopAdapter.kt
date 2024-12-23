package com.example.plantapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.models.Product

class RVShopAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<RVShopAdapter.ShopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        // Inflate the item layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_shop_item, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        // Bind data to the item
        val product = productList[position]
        holder.tvProductName.text = product.name
        holder.tvProductPrice.text = product.price
        holder.image.setImageResource(product.imageResId)
    }

    override fun getItemCount(): Int = productList.size

    // ViewHolder class
    class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val image: ImageView = itemView.findViewById(R.id.ivDisplay)
    }
}
