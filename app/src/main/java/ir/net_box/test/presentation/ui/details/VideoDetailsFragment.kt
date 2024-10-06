package ir.net_box.test.presentation.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.app.DetailsSupportFragmentBackgroundController
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.OnActionClickedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ir.net_box.test.R
import ir.net_box.test.data.local.model.VideoEntity
import ir.net_box.test.presentation.ui.MainActivity
import ir.net_box.test.presentation.ui.details.DetailsActivity.Companion.VIDEO
import ir.net_box.test.presentation.ui.playback.PlaybackActivity

class VideoDetailsFragment : DetailsSupportFragment() {

    private var mSelectedMovie: VideoEntity? = null

    private lateinit var mDetailsBackground: DetailsSupportFragmentBackgroundController
    private lateinit var mPresenterSelector: ClassPresenterSelector
    private lateinit var mAdapter: ArrayObjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDetailsBackground = DetailsSupportFragmentBackgroundController(this)

        mSelectedMovie =
            activity?.intent?.getSerializableExtra(DetailsActivity.VIDEO) as VideoEntity
        if (mSelectedMovie != null) {
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
            adapter = mAdapter
            initializeBackground(mSelectedMovie)
        } else {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeBackground(videoEntity: VideoEntity?) {
        mDetailsBackground.enableParallax()
        activity?.let {
            Glide.with(it)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.default_background)
                .load(videoEntity?.thumbnail)
                .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        bitmap: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        mDetailsBackground.coverBitmap = bitmap
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                    }
                })
        }
    }

    private fun setupDetailsOverviewRow() {
        val row = DetailsOverviewRow(mSelectedMovie)
        row.imageDrawable =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.default_background) }
        val width = convertDpToPixel(activity, DETAIL_THUMB_WIDTH)
        val height = convertDpToPixel(activity, DETAIL_THUMB_HEIGHT)
        activity?.let {
            Glide.with(it)
                .load(mSelectedMovie?.thumbnail)
                .centerCrop()
                .error(R.drawable.default_background)
                .into<SimpleTarget<Drawable>>(object : SimpleTarget<Drawable>(width, height) {
                    override fun onResourceReady(
                        drawable: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        row.imageDrawable = drawable
                        mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                    }
                })
        }

        val actionAdapter = ArrayObjectAdapter()

        actionAdapter.add(
            Action(
                ACTION_WATCH_TRAILER,
                resources.getString(R.string.play_video)
            )
        )
        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        activity?.let {
            detailsPresenter.backgroundColor =
                ContextCompat.getColor(it, R.color.selected_background)
        }
        detailsPresenter.isParticipatingEntranceTransition = true
        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            if (action.id == ACTION_WATCH_TRAILER) {
                val intent = Intent(activity!!, PlaybackActivity::class.java)
                intent.putExtra(VIDEO, mSelectedMovie)
                startActivity(intent)
            }
        }
        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    private fun convertDpToPixel(context: Context?, dp: Int): Int {
        val density = context?.applicationContext?.resources?.displayMetrics?.density
        return Math.round(dp.toFloat() * (density ?: 0F))
    }

    companion object {
        private val ACTION_WATCH_TRAILER = 1L
        private val DETAIL_THUMB_WIDTH = 274
        private val DETAIL_THUMB_HEIGHT = 274
    }
}
