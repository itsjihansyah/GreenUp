package com.example.plantapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.models.Product

class ShopFragment : Fragment() {

    private lateinit var rvMainProductsList: RecyclerView
    private lateinit var shopAdapter: RVShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shop, container, false)

        // Initialize RecyclerView
        rvMainProductsList = view.findViewById(R.id.rvMainProductsList)

        // Set GridLayoutManager with 2 columns
        val gridLayoutManager = GridLayoutManager(context, 2)
        rvMainProductsList.layoutManager = gridLayoutManager

        // Initialize Adapter and set to RecyclerView
        shopAdapter = RVShopAdapter(getDummyData())
        rvMainProductsList.adapter = shopAdapter

        return view
    }

    private fun getDummyData(): List<Product> {
        return listOf(
            Product("Monstera", "$10", R.drawable.monstera), // Add actual image resource IDs here
            Product("Lavender", "$20", R.drawable.lavender),
            Product("Sanseviera", "$30", R.drawable.sanseviera),
            Product("Tropical Plant", "$40", R.drawable.tropical_plant),
            Product("Epipremnum", "$50", R.drawable.lavender),
            Product("Lily", "$60", R.drawable.monstera)
        )
    }
}
