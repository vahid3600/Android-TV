package ir.net_box.test.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.net_box.test.data.local.AppDatabase
import ir.net_box.test.data.local.model.VideoEntity
import ir.net_box.test.data.mapper.toVideoEntity
import ir.net_box.test.data.remote.NetBoxApi
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val netBoxApi: NetBoxApi,
    private val appDatabase: AppDatabase
) : VideoRepository {
    override fun getPlaylist(): PagingSource<Int, VideoEntity> {
        return object : PagingSource<Int, VideoEntity>() {
            override fun getRefreshKey(state: PagingState<Int, VideoEntity>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoEntity> {
                val position = params.key ?: 1
                val videos = try {
                    netBoxApi.getPlaylist(position).videos
                } catch (exception: Exception) {
                    emptyList()
                }
                appDatabase.videoDao().insertAllVideos(videos.map { it.toVideoEntity() })
                val videoEntities = appDatabase.videoDao().getAllVideos(10, ((position - 1) * 10))
                return LoadResult.Page(
                    data = videoEntities,
                    prevKey = null,
                    nextKey = if (videos.isEmpty()) null else (position + 1)
                )
            }

        }
    }
}