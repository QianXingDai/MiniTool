package com.kakacat.minitool.common.util

import bolts.Task
import java.util.concurrent.Callable

object ThreadUtil {
    @JvmStatic
    fun callInUiThread(runnable: Runnable) {
        Task.call(runToCall(runnable), Task.UI_THREAD_EXECUTOR)
    }

    @JvmStatic
    fun callInBackground(runnable: Runnable) {
        Task.callInBackground(runToCall(runnable))
    }

    private fun runToCall(runnable: Runnable): Callable<Void?> {
        return Callable {
            runnable.run()
            null
        }
    }
}