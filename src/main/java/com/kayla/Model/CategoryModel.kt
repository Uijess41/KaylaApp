package com.kayla.Model

data class CategoryModel(
    val status: Boolean, // true
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 4
        val name: String, // Wedding
        val image: String, // 6.png
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}