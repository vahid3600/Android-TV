package ir.net_box.test.presentation.ui.details

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import ir.net_box.test.data.local.model.VideoEntity

class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {

    override fun onBindDescription(
        viewHolder: ViewHolder,
        item: Any
    ) {
        val video = item as VideoEntity
        viewHolder.title.text = video.name
        viewHolder.body.text = video.description
    }
}