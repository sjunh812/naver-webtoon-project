package org.sjhstudio.naverwebtoon.ui.episode.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.naverwebtoon.domain.model.Episode
import org.sjhstudio.naverwebtoon.domain.model.WebtoonInfo
import org.sjhstudio.naverwebtoon.domain.repository.WebtoonRepository
import org.sjhstudio.naverwebtoon.ui.weekday.view.WeekdayFragment.Companion.TITLE_ID
import org.sjhstudio.naverwebtoon.ui.weekday.view.WeekdayFragment.Companion.WEEKDAY
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WebtoonRepository
) : ViewModel() {

    private val weekday: String = savedStateHandle[WEEKDAY] ?: throw IllegalStateException()
    private val titleId: Long = savedStateHandle[TITLE_ID] ?: throw IllegalStateException()

    private var _webtoonInfo = MutableStateFlow<WebtoonInfo?>(null)
    val webtoonInfo = _webtoonInfo.asStateFlow()

    private var _episodePagingData = MutableStateFlow<PagingData<Episode>?>(null)
    val episodePagingData = _episodePagingData.asStateFlow()

    var detailExpanded = false

    init {
        getWebToonInfo()
        getEpisodePagingData()
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

    private fun getEpisodePagingData() = viewModelScope.launch {
        repository.getEpisodePagingData(titleId, weekday)
            .cachedIn(viewModelScope)
            .onStart { }
            .onCompletion { }
            .catch { }
            .collectLatest { pagingData ->
                _episodePagingData.emit(pagingData)
            }
    }
}