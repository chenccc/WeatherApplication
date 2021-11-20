package com.james.weatherapplication.ui.manager

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import com.james.weatherapplication.ui.manager.BlockActionManager.Companion.INTERVAL_LONG
import java.util.concurrent.atomic.AtomicBoolean

class BlockActionManager(private val interval: Long) {
    companion object {
        const val INTERVAL_LONG = 1500L
        const val INTERVAL_SHORT = 250L
    }

    private val isRunning = AtomicBoolean(false)
    private lateinit var timer: CountDownTimer

    init {
        Handler(Looper.getMainLooper()).post {
            timer = object : CountDownTimer(interval, interval) {
                override fun onFinish() {
                    isRunning.set(false)
                }
                override fun onTick(millisUntilFinished: Long) {}
            }
        }
    }

    @Synchronized
    fun handleTask(action: () -> Unit) {
        if (isRunning.compareAndSet(false, true)) {
            timer.cancel()
            action.invoke()
            timer.start()
        }
    }
}

class BlockActionClickListener(interval: Long, private val listener: ((view: View?) -> Unit?)) :
    View.OnClickListener {
    constructor(interval: Long, onClickListener: View.OnClickListener?) : this(
        interval = interval,
        listener = {
            onClickListener?.onClick(it)
        })

    private val blockActionManager = BlockActionManager(interval)
    override fun onClick(v: View?) {
        blockActionManager.handleTask {
            listener.invoke(v)
        }
    }
}

fun ((view: View?) -> Unit?).toBlockActionClickListener(interval: Long = INTERVAL_LONG): View.OnClickListener {
    return BlockActionClickListener(interval = interval, listener = this)
}

fun View.OnClickListener?.toBlockActionClickListener(interval: Long = INTERVAL_LONG): View.OnClickListener {
    return if (this is BlockActionClickListener) {
        this
    } else {
        BlockActionClickListener(
            interval = interval,
            onClickListener = this
        )
    }
}