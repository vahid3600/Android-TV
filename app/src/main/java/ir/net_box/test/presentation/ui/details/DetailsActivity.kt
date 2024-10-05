package ir.net_box.test.presentation.ui.details

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import ir.net_box.test.R

class DetailsActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.details_fragment, VideoDetailsFragment())
                .commitNow()
        }
    }

    companion object {
        const val VIDEO = "Video"
    }
}