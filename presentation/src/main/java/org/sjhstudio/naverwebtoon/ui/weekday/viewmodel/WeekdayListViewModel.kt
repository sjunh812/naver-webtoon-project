package org.sjhstudio.naverwebtoon.ui.weekday.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.naverwebtoon.domain.model.NewWebtoon
import org.sjhstudio.naverwebtoon.domain.model.WeekdayWebtoon
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

@HiltViewModel
class WeekdayListViewModel @Inject constructor(
    private val repository: WebToonRepository
) : ViewModel() {

    private var _weekdayList = MutableStateFlow<Map<String, List<WeekdayWebtoon>>>(emptyMap())
    val weekdayList = _weekdayList.asStateFlow()

    private var _newList = MutableStateFlow<List<NewWebtoon>>(emptyList())
    val newList = _newList.asStateFlow()

    init {
        getWeekdayList()
    }

    private fun getWeekdayList() = viewModelScope.launch {
        repository.getWeekdayWebToonList()
            .onStart { }
            .onCompletion { }
            .catch { e -> e.printStackTrace() }
            .collectLatest { map -> _weekdayList.emit(map) }
    }

    fun getNewWebToonList(html: String) = viewModelScope.launch {
        repository.getNewWebToonList(html)
            .onStart { }
            .onCompletion { }
            .catch { e -> e.printStackTrace() }
            .collectLatest { list -> _newList.emit(list) }
    }
}