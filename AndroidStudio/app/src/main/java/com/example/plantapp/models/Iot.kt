package com.example.plantapp.models

import com.google.firebase.database.PropertyName

data class Iot(
    @PropertyName("id") var id: String? = null,
    @PropertyName("health") var health: String? = "N/A",  // Default health value
    @PropertyName("temp") var temp: Int? = 0,             // Default temperature value
    @PropertyName("light") var light: Int? = 0,           // Default light value
    @PropertyName("moist") var moist: Int? = 0            // Default moisture value
) {
    // No-argument constructor is required by Firebase
    constructor() : this(id = "", health = "N/A", temp = 0, light = 0, moist = 0)
}
