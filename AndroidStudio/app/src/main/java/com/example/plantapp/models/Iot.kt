package com.example.plantapp.models

import com.google.firebase.database.PropertyName

data class Iot(
    @PropertyName("id") var id: String? = null,
    @PropertyName("health") var health: String? = "N/A",
    @PropertyName("temp") var temp: Int? = 0,
    @PropertyName("light") var light: Int? = 0,
    @PropertyName("moist") var moist: Int? = 0
) {

    constructor() : this(id = "", health = "N/A", temp = 0, light = 0, moist = 0)
}
