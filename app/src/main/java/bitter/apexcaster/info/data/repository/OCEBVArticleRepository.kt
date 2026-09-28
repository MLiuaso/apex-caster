package bitter.apexcaster.info.data.repository

import bitter.apexcaster.info.data.local.dao.OCEBVArticleDao
import bitter.apexcaster.info.data.local.entity.OCEBVArticleEntity
import kotlinx.coroutines.flow.Flow

//[@AGENT][Read-only access to the editorial articles shown in Home's discover carousel and
// opened full-screen by ArticleDetailScreen. Unlike workouts, articles are static app content:
// they are seeded once into Room on first database open and are never created or edited by the
// user, so this repository intentionally exposes no write operations.]
class OCEBVArticleRepository(private val articleDao: OCEBVArticleDao) {
    val allArticles: Flow<List<OCEBVArticleEntity>> = articleDao.getAllArticles()

    suspend fun getArticleById(id: Long): OCEBVArticleEntity? {
        return articleDao.getArticleById(id)
    }
}
