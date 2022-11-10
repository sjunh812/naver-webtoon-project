package org.sjhstudio.naverwebtoon.ui.viewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.naverwebtoon.domain.repository.WebtoonRepository
import org.sjhstudio.naverwebtoon.ui.episode.view.EpisodeListActivity.Companion.DATA_NO
import org.sjhstudio.naverwebtoon.ui.episode.view.EpisodeListActivity.Companion.TITLE
import org.sjhstudio.naverwebtoon.ui.episode.view.EpisodeListActivity.Companion.TITLE_ID
import javax.inject.Inject

@HiltViewModel
class ViewerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WebtoonRepository
) : ViewModel() {

    val titleId: Long = savedStateHandle[TITLE_ID] ?: throw IllegalStateException()
    val title: String = savedStateHandle[TITLE] ?: throw IllegalStateException()
    val dataNo: Long = savedStateHandle[DATA_NO] ?: throw IllegalStateException()

    private var _viewerList = MutableStateFlow<PagingData<String>>(PagingData.empty())
    val viewerList = _viewerList.asStateFlow()

    var showBars = true

    init {
        getViewerPagingData()
    }

    private fun getViewerPagingData() = viewModelScope.launch {
        repository.getViewerPagingData(titleId, dataNo)
            .cachedIn(viewModelScope)
            .onStart { }
            .onCompletion { }
            .catch { e -> e.printStackTrace() }
            .collectLatest { pagingData ->
                _viewerList.emit(pagingData)
            }
    }
}