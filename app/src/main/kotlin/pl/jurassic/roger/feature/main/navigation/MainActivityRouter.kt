package pl.jurassic.roger.feature.main.navigation

import android.content.Intent
import pl.jurassic.roger.feature.main.MainActivityContract
import pl.jurassic.roger.feature.main.ui.MainActivity
import pl.jurassic.roger.feature.summary.ui.SummaryActivity

class MainActivityRouter(private val activity: MainActivity) : MainActivityContract.Router {

    override fun navigateToSummaryScreen() {
        val intent = Intent(activity, SummaryActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        activity.startActivity(intent)
    }
}