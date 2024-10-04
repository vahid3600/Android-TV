package ir.net_box.test.data.mapper

import ir.net_box.test.data.local.model.VideoEntity
import ir.net_box.test.data.remote.model.Video

fun Video.toVideoEntity() = VideoEntity(
    videoId = id,
    name = name,
    description = description,
    thumbnail = thumbnail
)