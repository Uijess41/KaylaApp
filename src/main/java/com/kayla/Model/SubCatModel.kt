package com.kayla.Model

data class  SubCatModel(
    val status: Boolean, // true
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 57
        val category_id: String, // 5
        val sub_category_id: String, // 4
        val product_title: String, // first test product name
        val product_description: String, // product description
        val MRP: String, // 100
        val discount: String,
        val selling_price: String, // 90
        val image: String, // download (2).jpg,download (3).jpg,download (4).jpg
        val stock: String, // 10
        val color: String, // pink
        val size: String, // m
        val brand: String, // 7
        val status: String, // 0
        val type: String, // 0
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}