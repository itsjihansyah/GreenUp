package com.example.plantapp.models

import androidx.lifecycle.ViewModel

data class Mission(
    val missionName: String = "",
    var isActive: Boolean = false,
    val rewardPoints: Int = 0
)
