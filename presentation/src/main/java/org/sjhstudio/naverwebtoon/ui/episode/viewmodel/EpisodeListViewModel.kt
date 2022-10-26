package org.sjhstudio.naverwebtoon.ui.episode.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.naverwebtoon.domain.model.WebtoonInfo
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import org.sjhstudio.naverwebtoon.ui.episode.view.EpisodeListActivity.Companion.TITLE_ID
import org.sjhstudio.naverwebtoon.ui.episode.view.EpisodeListActivity.Companion.WEEKDAY
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WebToonRepository
) : ViewModel() {

    private val weekday: String = savedStateHandle[WEEKDAY] ?: throw IllegalStateException()
    private val titleId: Long = savedStateHandle[TITLE_ID] ?: throw IllegalStateException()

    var detailExpanded = false

    private var _webtoonInfo = MutableStateFlow<WebtoonInfo?>(null)
    val webtoonInfo = _webtoonInfo.asStateFlow()

    init {
        getWebToonInfo()
    }

    private fun getWebToonInfo() = viewModelScope.launch {
        repository.getWebToonInfo(titleId, weekday)
            .onStart { }
            .onCompletion { }
            .catch { e -> e.printStackTrace() }
            .collectLatest { data ->
                _webtoonInfo.emit(data)
            }
    }
}