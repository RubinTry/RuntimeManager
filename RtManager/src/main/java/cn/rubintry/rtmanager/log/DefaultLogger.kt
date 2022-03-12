package cn.rubintry.rtmanager.log

import android.util.Log

class DefaultLogger : ILogger {
    private val TAG = "RuntimeManager: ===>"

    override fun debug(msg: String) {
        Log.d(TAG, msg)
    }
}