package bitter.apexcaster.info.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bitter.apexcaster.info.data.local.entity.OCEBVArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OCEBVArticleDao {
    @Query("SELECT * FROM articles") fun getAllArticles(): Flow<List<OCEBVArticleEntity>>

    @Query("SELECT COUNT(*) FROM articles") suspend fun getArticleCount(): Int

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: Long): OCEBVArticleEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<OCEBVArticleEntity>)
}
