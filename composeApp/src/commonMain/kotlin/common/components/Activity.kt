package common.components

data class Activity(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    var likesCount: Int
)
