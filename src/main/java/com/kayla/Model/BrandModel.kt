package com.kayla.Model

data class BrandModel(
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 7
        val name: String, // kisan
        val image: String, // 91TfUdfs5ZL._AC_UL320_.jpg
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}