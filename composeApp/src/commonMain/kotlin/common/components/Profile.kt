package common.components

data class Profile(
    val name: String,
    val email: String,
    val bio: String,
    val badgeCount: Int,
    val eventCount: Int,
    val imageUrl: String
)
