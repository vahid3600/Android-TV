package ir.net_box.test.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.net_box.test.data.repository.VideoRepository
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    videoRepository: VideoRepository
) : ViewModel() {

    val videos = Pager(
        config = PagingConfig(pageSize = 10)
    ) {
        videoRepository.getPlaylist()
    }.flow
        .cachedIn(viewModelScope)
}