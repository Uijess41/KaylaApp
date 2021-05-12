package com.kayla.Model

data class FeatureModel(
    val status: Boolean, // true
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 72
        val category_id: String, // 5
        val sub_category_id: String, // 4
        val product_title: String, // jeans
        val product_description: String, // ssss
        val MRP: String, // 100
        val discount: String,
        val selling_price: String, // 90
        val image: String, // 3.png
        val stock: String, // 10
        val color: String, // red
        val size: String, // 424
        val brand: String, // 8
        val status: String, // 0
        val type: String, // Featured
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}