package com.kayla.Model

data class SliderImageData(
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 4
        val title: String, // test bannere update 
        val image: String, // equimg.png
        val path: String // http://ruparnatechnology.com/Kayla/image/
    )
}