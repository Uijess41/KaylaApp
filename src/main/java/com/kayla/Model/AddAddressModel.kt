package com.kayla.Model

data class AddAddressModel(
    val result: String, // successfully
    val `data`: Data
) {
    data class Data(
        val id: String, // 5
        val user_id: String, // 2
        val name: String, // amit
        val address: String, // mandideep
        val pincode: String, // 462046
        val city: String, // Mandideep
        val phone_number: String // 1236547890
    )
}