package org.sjhstudio.naverwebtoon.ui.weekday.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.naverwebtoon.domain.model.MobileWebToon
import org.sjhstudio.naverwebtoon.domain.model.WebToon
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

@HiltViewModel
class WeekdayListViewModel @Inject constructor(
    private val repository: WebToonRepository
) : ViewModel() {

    private var _weekdayList = MutableStateFlow<Map<String, List<MobileWebToon>>>(emptyMap())
    val weekdayList = _weekdayList.asStateFlow()

    init {
        getWeekdayList()
    }

    private fun getWeekdayList() = viewModelScope.launch {
        repository.getMobileWeekdayList()
            .onStart { }
            .onCompletion { }
            .catch { e -> e.printStackTrace() }
            .collectLatest { map -> _weekdayList.emit(map) }
    }
}