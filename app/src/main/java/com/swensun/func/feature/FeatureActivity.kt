package com.swensun.func.feature

import android.os.Bundle
import com.swensun.base.BaseActivity
import com.swensun.potato.R
import com.swensun.potato.databinding.FeatureActivityBinding
import com.swensun.swutils.ui.toast

class FeatureActivity : BaseActivity<FeatureActivityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        toast("FeatureActivity create")
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FeatureFragment.newInstance())
                .commitNow()
        }
    }
}
