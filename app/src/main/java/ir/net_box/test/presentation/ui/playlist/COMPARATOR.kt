package ir.net_box.test.presentation.ui.playlist

import androidx.recyclerview.widget.DiffUtil
import ir.net_box.test.data.local.model.VideoEntity

val COMPARATOR = object : DiffUtil.ItemCallback<VideoEntity>() {
    override fun areItemsTheSame(oldItem: VideoEntity, newItem: VideoEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoEntity, newItem: VideoEntity): Boolean =
        oldItem == newItem
}