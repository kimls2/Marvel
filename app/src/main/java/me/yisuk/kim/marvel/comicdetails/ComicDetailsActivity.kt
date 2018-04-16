package me.yisuk.kim.marvel.comicdetails

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_comic_details.*
import me.yisuk.kim.marvel.R
import me.yisuk.kim.marvel.comicdetails.details.ComicDetailsFragment
import me.yisuk.kim.marvel.extentions.replaceFragmentInActivity


/**
 * @author <a href="kimls125@gmail.com">Yisuk Kim</a> on 11-04-2018.
 */
class ComicDetailsActivity : DaggerAppCompatActivity() {

    companion object {
        private const val KEY_COMIC_ID = "comic_id"
        fun createIntent(context: Context, id: Long): Intent {
            return Intent(context, ComicDetailsActivity::class.java).apply {
                putExtra(KEY_COMIC_ID, id)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_details)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.trans)

        postponeEnterTransition()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        super.onNewIntent(intent)
        val comicId = intent.getLongExtra(KEY_COMIC_ID, -1L)
        if (comicId != -1L) {
            replaceFragmentInActivity(ComicDetailsFragment.create(comicId), details_content.id)
        } else {
            finish()
        }
    }

    override fun finishAfterTransition() {
        val resultData = Intent()
        val result = onPopulateResultIntent(resultData)
        setResult(result, resultData)

        super.finishAfterTransition()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onPopulateResultIntent(intent: Intent): Int {
        return Activity.RESULT_OK
    }

}