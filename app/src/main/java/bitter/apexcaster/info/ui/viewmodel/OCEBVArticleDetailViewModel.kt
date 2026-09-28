package bitter.apexcaster.info.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import bitter.apexcaster.info.data.local.entity.OCEBVArticleEntity
import bitter.apexcaster.info.data.repository.OCEBVArticleRepository

class OCEBVArticleDetailViewModel(
    private val articleRepository: OCEBVArticleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val articleId: Long? = savedStateHandle["articleId"]

    private val _article = MutableStateFlow<OCEBVArticleEntity?>(null)
    val article: StateFlow<OCEBVArticleEntity?> = _article

    init {
        articleId?.let { id ->
            viewModelScope.launch {
                _article.value = articleRepository.getArticleById(id)
            }
        }
    }
}
