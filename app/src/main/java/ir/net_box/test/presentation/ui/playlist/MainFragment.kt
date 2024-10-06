package ir.net_box.test.presentation.ui.playlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.paging.PagingDataAdapter
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.Row
import androidx.leanback.widget.RowPresenter
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.net_box.test.data.local.model.VideoEntity
import ir.net_box.test.presentation.ui.details.DetailsActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BrowseSupportFragment() {

    private val viewModel by viewModels<VideoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initValues()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadRows()
        onItemViewClickedListener = ItemViewClickedListener()
    }

    private fun initValues() {
        headersState = HEADERS_DISABLED
    }

    private fun loadRows() {
        val cardPresenter = CardPresenter()
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        val pagingAdapter: PagingDataAdapter<VideoEntity> =
            PagingDataAdapter(presenter = cardPresenter, COMPARATOR)
        rowsAdapter.add(ListRow(pagingAdapter))
        adapter = rowsAdapter
        lifecycleScope.launch {
            viewModel.videos.collectLatest {
                pagingAdapter.submitData(it)
            }
        }
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {

            if (item is VideoEntity) {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.VIDEO, item)
                startActivity(intent)
            }
        }
    }
}