package com.kayla.Model

data class SubcattitleModel(
    val status: Boolean, // true
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 4
        val name: String, // Jeans
        val image: String, // men.jpg
        val category_id: String, // 5
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}