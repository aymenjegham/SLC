package com.aymen.slc.ui.main.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.aymen.slc.R
import com.aymen.slc.databinding.ActivitySplashBinding
import com.aymen.slc.global.helpers.Navigation
import com.aymen.slc.global.utils.IS_CONNECTED_KEY
import com.aymen.slc.ui.base.BaseActivity
import com.aymen.slc.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {


    val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        registerBaseObservers(viewModel)


    }

    override fun navigate(navigationTo: Navigation) {
        if (navigationTo is Navigation.Home) {
            Intent(this@SplashActivity, MainActivity::class.java)
                .apply {
                    putExtra(IS_CONNECTED_KEY, navigationTo.isConnected)
                    startActivity(this)
                    finish()
                }
        }
    }

}