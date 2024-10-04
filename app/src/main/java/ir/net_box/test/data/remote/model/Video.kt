package ir.net_box.test.data.remote.model

import com.google.gson.annotations.SerializedName

data class Video(
    var id: Int,
    var name: String,
    var description: String,
    var thumbnail: String?,
    @SerializedName("publish_date")
    var publishDate: String,
    var file: String,
    @SerializedName("file_src")
    var fileSrc: String,
    var quality: Int,
    @SerializedName("file_size")
    var fileSize: Int,
    var duration: Int,
    @SerializedName("content_type")
    var contentType: Int,
    @SerializedName("created_date")
    var createdDate: String
)