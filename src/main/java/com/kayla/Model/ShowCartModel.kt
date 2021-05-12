package com.kayla.Model

data class ShowCartModel(
    val id: String, // 1
    val user_id: String, // 1
    val seller_id: String, // 0
    val product_id: String, // 77
    val qntity: Int, // 9
    val product_price: String, // 90
    val total_price: String, // 180
    val check_out_status: String, // 0
    val strtotime: String, // 1617711399
    val total_amount: Int, // 724
    val `data`: List<Data>
) {
    data class Data(
        val id: String, // 1
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
        val qntity: String, // 2
        val path: String, // http://ruparnatechnology.com/Kayla/image/
        val sub_total: String // 180
    )
}