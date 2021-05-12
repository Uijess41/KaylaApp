package com.kayla.Activity

data class SubCatData(
    val status: Boolean, // true
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 73
        val category_id: String, // 5
        val sub_category_id: String, // 5
        val product_title: String, // first test product name
        val product_description: String, // sdfdf
        val MRP: String, // 100
        val discount: String,
        val selling_price: String, // 90
        val image: String, // 91TfUdfs5ZL._AC_UL320_.jpg
        val stock: String, // 10
        val color: String, // red
        val size: String, // m
        val brand: String, // 7
        val status: String, // 0
        val type: String,
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}