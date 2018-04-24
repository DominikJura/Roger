package pl.jurassic.roger.feature.main.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import pl.jurassic.roger.R
import pl.jurassic.roger.feature.common.ui.BaseFragment
import pl.jurassic.roger.feature.main.MainFragmentContract.Presenter
import pl.jurassic.roger.feature.main.MainFragmentContract.View
import pl.jurassic.roger.util.timer.BreakType
import pl.jurassic.roger.util.timer.TimerService
import pl.jurassic.roger.util.timer.TimerServiceBinder
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_main.main_break_item_lunch as breakItemLunch
import kotlinx.android.synthetic.main.fragment_main.main_break_item_other as breakItemOther
import kotlinx.android.synthetic.main.fragment_main.main_break_item_smoking as breakItemSmoking
import kotlinx.android.synthetic.main.fragment_main.main_job_time_text as jobTimeTextView
import kotlinx.android.synthetic.main.fragment_main.main_break_time_text as breakTimeTextView
import kotlinx.android.synthetic.main.fragment_main.main_timer_button as timerImageView
import kotlinx.android.synthetic.main.fragment_main.main_save_button as saveButton

class MainFragment : BaseFragment<Presenter>(), View {

    @Inject
    lateinit var timerServiceIntent: Intent

    val timerServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerServiceBinder
            timerService = binder.service
            timerServiceBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            timerServiceBound = false
        }
    }

    override var activeBreakType: BreakType? = null

    override val layoutId: Int = R.layout.fragment_main

    override lateinit var timerService: TimerService

    override var timerServiceBound: Boolean = false
        set(value) {
            field = value
            if (value) {
                timerService.timeUpdateCallback = { presenter.onJobTimeReceive(it) }
                timerService.breakUpdateCallback = { presenter.onBreakTimeReceive(it) }
            } else {
                timerService.timeUpdateCallback = null
                timerService.breakUpdateCallback = null
            }
        }

    override fun initOnClickListeners() = with(presenter) {
        timerImageView.setOnClickListener { onTimerButtonClicked() }
        breakItemSmoking.setOnClickListener { onSmokingItemClicked(it.isSelected) }
        breakItemLunch.setOnClickListener { onLunchItemClicked(it.isSelected) }
        breakItemOther.setOnClickListener { onOtherItemClicked(it.isSelected) }
        saveButton.setOnClickListener { onSaveClicked() }
    }

    override fun onResume() {
        super.onResume()
        activity?.bindService(timerServiceIntent, timerServiceConnection, Context.BIND_AUTO_CREATE)
        activity?.startService(timerServiceIntent)
    }

    override fun onPause() {
        super.onPause()
        if (timerServiceBound) {
            activity?.unbindService(timerServiceConnection)
            timerServiceBound = false
        }
    }

    override fun setJobTime(jobTime: String) {
        jobTimeTextView.text = jobTime
    }

    override fun setBreakTime(breakTime: String) {
        breakTimeTextView.text = breakTime
    }

    override fun activeSmokingButton() {
        breakItemSmoking.isSelected = true
    }

    override fun deactivateSmokingButton() {
        breakItemSmoking.isSelected = false
    }

    override fun activeLunchButton() {
        breakItemLunch.isSelected = true
    }

    override fun deactivateLunchButton() {
        breakItemLunch.isSelected = false
    }

    override fun activeOtherButton() {
        breakItemOther.isSelected = true
    }

    override fun deactivateOtherButton() {
        breakItemOther.isSelected = false
    }

    override fun setTimerPause() {
        timerImageView.isSelected = false
    }

    override fun setTimerStart() {
        timerImageView.isSelected = true
    }
}