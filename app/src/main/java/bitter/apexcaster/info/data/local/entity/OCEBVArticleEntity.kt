package bitter.apexcaster.info.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class OCEBVArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val subtitle: String,
    val content: String,
    val imageUrl: String,
    val category: String,
    val isFavorite: Boolean = false
)
