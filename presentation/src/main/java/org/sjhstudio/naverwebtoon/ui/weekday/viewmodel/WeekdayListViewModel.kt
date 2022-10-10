package org.sjhstudio.naverwebtoon.ui.weekday.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.sjhstudio.naverwebtoon.domain.model.WebToon
import org.sjhstudio.naverwebtoon.domain.repository.WebToonRepository
import javax.inject.Inject

@HiltViewModel
class WeekdayListViewModel @Inject constructor(
    private val repository: WebToonRepository
) : ViewModel() {

    fun getWeekdayList(week: String): Flow<List<WebToon>> =
        repository.getWeekdayList(week)
}