package com.kayla.Model

data class ArrivalHomeModel1(
    val status: Boolean, // true
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 76
        val category_id: String, // 5
        val sub_category_id: String, // 4
        val product_title: String, // asda
        val product_description: String, // ds
        val MRP: String, // 100
        val discount: String,
        val selling_price: String, // 90
        val image: String, // men.jpg
        val stock: String, // 10
        val color: String, // blue
        val size: String, // m
        val brand: String, // 8
        val status: String, // 0
        val type: String, // New_Arrival
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}