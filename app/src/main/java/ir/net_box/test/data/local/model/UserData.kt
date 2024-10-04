package ir.net_box.test.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val videoId: Int,
    val name: String,
    var description: String,
    var thumbnail: String?
)