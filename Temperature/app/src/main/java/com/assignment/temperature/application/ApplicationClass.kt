package com.assignment.temperature

import android.annotation.SuppressLint
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.assignment.temperature.R
import com.zuba.networkUtil.apiConfig.NetworkUtilites


open class ApplicationClass : Application() {

    init {
        mCurrentInstance = this
    }

    var mActivity: AppCompatActivity? = null
    private var mProgressDialog: ProgressDialog? = null
    private var mAlertDialog: AlertDialog? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mCurrentInstance: Application? = null

        fun getApplicationContext(): Context {
            return mCurrentInstance!!.applicationContext
        }
    }


    fun showAlert(pMessage: String, pContext: Context?) {
        if (pContext != null) {
            if (this.mAlertDialog == null || !this.mAlertDialog!!.isShowing) {
                val builder = AlertDialog.Builder(pContext)
                builder.setTitle(pContext.getString(R.string.alert_title))
                    .setMessage(pMessage)
                    .setCancelable(false)
                    .setPositiveButton(pContext.resources.getString(R.string.ok))
                    { dialog, _ ->
                        dialog.dismiss()
                    }
                mAlertDialog = builder.create()
                mAlertDialog!!.show()
            }
        }
    }


    fun setActivity(pActivity: AppCompatActivity) {
        mActivity = pActivity
    }

    fun getmActivity(): AppCompatActivity? {
        return mActivity
    }

    fun isConnected(): Boolean {
        if (mActivity != null && !mActivity!!.isFinishing) {
            if (!NetworkUtilites.isInternetAvailable(mActivity!!) && !mActivity!!.isFinishing) {
                showAlert(
                    mActivity!!.resources.getString(R.string.check_connection),
                    mActivity
                )
                return false
            }
        } else {
            return false
        }
        return true
    }

    fun showProgress(pContext: Context?) {
        // If our activity is going to finish then we don't need to show any progress.
        if (pContext != null) {
            val activity = pContext as AppCompatActivity?
            if (activity!!.isFinishing)
                return
            if (this.mProgressDialog == null || !this.mProgressDialog!!.isShowing) {
                mProgressDialog = ProgressDialog.show(pContext, null, null)
                val spinner = ProgressBar(pContext, null, android.R.attr.progressBarStyle)
                spinner.indeterminateDrawable.setColorFilter(
                    ContextCompat.getColor(
                        pContext,
                        R.color.colorAccent
                    ), android.graphics.PorterDuff.Mode.SRC_IN
                )
                if (mProgressDialog!!.window != null) {
                    mProgressDialog!!.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                }
                mProgressDialog!!.setContentView(spinner)
                mProgressDialog!!.setCancelable(false)
                mProgressDialog!!.show()
            }
        }
    }

    fun hideProgressDialog() {
        if (this.mProgressDialog != null && this.mProgressDialog!!.isShowing) {
            this.mProgressDialog!!.dismiss()
            this.mProgressDialog = null
        }
    }
}
