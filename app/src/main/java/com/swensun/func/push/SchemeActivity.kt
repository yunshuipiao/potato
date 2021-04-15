package com.swensun.func.push

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.swensun.potato.MainActivity
import com.swensun.potato.R
import com.swensun.swutils.util.Logger
import kotlinx.android.synthetic.main.scheme_activity.*
import org.jetbrains.anko.toast
import java.util.logging.LogManager

class SchemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scheme_activity)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Logger.d("PushActivity__ onNewIntent")
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val uri = intent?.data
        if (uri != null) {
            if (ActivityUtils.getActivityList().all { it !is MainActivity }) {
                Logger.d("need startMainActivity")
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.data = uri
                startActivity(mainIntent)
                finish()
            }
            val action = uri.getQueryParameter("action") ?: ""
            val pkg = uri.getQueryParameter("pkg") ?: ""
            val url = uri.getQueryParameter("url") ?: ""
            when (action) {
                "1" -> {
                    //打开act

                }
                "2" -> {
                    if (pkg.isNotBlank() && AppUtils.isAppInstalled(pkg)) {
                        AppUtils.launchApp(pkg)
                    }
                }
                "3" -> {

                }
                else -> {
                    toast("action: $action")
                }
            }
        }
        tv_title.setOnClickListener {
            val acts = ActivityUtils.getActivityList()
            Logger.d("act: $acts")
        }
    }
}