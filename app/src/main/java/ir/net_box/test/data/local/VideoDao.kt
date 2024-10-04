package ir.net_box.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.net_box.test.data.local.model.VideoEntity

@Dao
interface VideoDao {
    @Insert
    suspend fun insertAllVideos(videos: List<VideoEntity>)

    @Query("SELECT * FROM videoentity LIMIT :limit OFFSET :offset ")
    suspend fun getAllVideos(limit: Int, offset: Int): List<VideoEntity>

    @Query("DELETE FROM videoentity")
    suspend fun clearAll()
}