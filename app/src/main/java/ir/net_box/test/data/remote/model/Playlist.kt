package ir.net_box.test.data.remote.model

import com.google.gson.annotations.SerializedName

data class Playlist(
    var id: Int,
    var name: String,
    var description: String,
    var thumbnail: String,
    @SerializedName("videos_count") var videosCount: Int,
    var videos: ArrayList<Video>
)