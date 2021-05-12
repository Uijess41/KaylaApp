package com.kayla.Model

data class ShowAddress(
    val result: Boolean, // true
    val `data`: Data
) {
    data class Data(
        val id: String, // 2
        val name: String, // update
        val email: String, // update
        val password: String, // password
        val aouth_id: String,
        val aouth_provider: String,
        val regid: String,
        val address: String, // new address update
        val city: String, // bhopal update
        val gender: String, // update
        val image: String,
        val phone_number: String, // 123 update
        val all_address: List<AllAddres>
    ) {
        data class AllAddres(
            val id: String, // 3
            val user_id: String, // 2
            val name: String, // updaet
            val address: String, // update
            val pincode: String, // 2222
            val city: String, // 333
            val phone_number: String // 11
        )
    }
}