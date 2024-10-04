package ir.net_box.test.data.repository

import androidx.paging.PagingSource
import ir.net_box.test.data.local.model.VideoEntity

interface VideoRepository {
    fun getPlaylist(): PagingSource<Int, VideoEntity>
}