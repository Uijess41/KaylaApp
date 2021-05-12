package com.kayla.Model

data class ShowProductDetailsModel(
    val status: Boolean, // true
    val `data`: Data
) {
    data class Data(
        val id: String, // 77
        val category_id: String, // 5
        val sub_category_id: String, // 6
        val product_title: String, // asda
        val product_description: String, // ssss
        val MRP: String, // 100
        val discount: String,
        val selling_price: String, // 90
        val image: String, // images.jpg
        val stock: String, // 10
        val color: String, // blue
        val size: String, // m
        val brand: String, // 7
        val status: String, // 0
        val type: String, // New_Arrival
        val cart_status: Boolean, // false
        val multi_image: List<MultiImage>,
        val path: String // http://ruparnatechnology.com/Kayla/image/
    ) {
        data class MultiImage(
            val id: String, // 43
            val product_id: String, // 77
            val image: String // images.jpg
        )
    }
}