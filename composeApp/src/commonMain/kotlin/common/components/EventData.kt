package common.components

data class EventData(
    val id: String,
    val type: String,
    val title: String,
    var description: String,
    val location: String,
    val date: String,
    var likesCount: Int,
    val imageUrl: String,
)
