package com.swensun.func.customview

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.CustomViewActivityBinding

class CustomViewActivity : BaseActivity<CustomViewActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CustomViewFragment.newInstance())
                .commitNow()
        }
    }
}
