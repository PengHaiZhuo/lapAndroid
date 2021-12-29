package com.phz.dev.feature.practice.screenrecord

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.Util
import com.gyf.immersionbar.ktx.immersionBar
import com.phz.common.ext.logE
import com.phz.common.page.activity.BaseVmDbPureActivity
import com.phz.common.state.NoViewModel
import com.phz.dev.R
import com.phz.dev.databinding.ActivityExoPlayerBinding
import kotlin.math.max

/**
 * @author phz on 2021/9/22
 * @description
 */
class ExoPlayerActivity : BaseVmDbPureActivity<NoViewModel, ActivityExoPlayerBinding>() {
    private var type: TYPE? = null
    private var url: String = ""
    private val playerEventListener: Player.Listener = playEventListener()
    private var player: SimpleExoPlayer? = null

    private var playWhenReady = true //可播放状态
    private var currentWindow = 0 //当前播放窗口索引
    private var playPosition = 0L //播放进度，单位ms（应对各种从其他界面切回来。举个例子，视频播放中途来个个电话，接完电话回来继续播）

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            playWhenReady = it.getBoolean(KEY_PLAY_WHEN_READY)
            currentWindow = it.getInt(KEY_CURRENT_WINDOW)
            playPosition = it.getLong(KEY_POSITION)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        releasePlayer()//在这里调用释放，因为当前活动启动模式为singleTask时，在栈内但是不在栈顶，从其他页面启动会调onNewIntent
        setIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        //隐藏SystemUi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        }
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
            mViewDataBinding.stylePlayer.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
            mViewDataBinding.stylePlayer.onPause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //保存一下状态，防止页面由于意外情况销毁丢失播放进度
        player?.run {
            //保存几个播放常量
            playPosition = max(0, currentPosition)
            currentWindow = currentWindowIndex
            this@ExoPlayerActivity.playWhenReady = playWhenReady
        }
        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady)
        outState.putLong(KEY_POSITION, playPosition)
        outState.putInt(KEY_CURRENT_WINDOW, currentWindow)
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector(this).apply {
            //setMaxVideoSizeSd即1279*769
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        player = SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build()
            .also { simpleExoPlayer ->
                //将播放器附加到视图
                mViewDataBinding.stylePlayer.player = simpleExoPlayer
                //添加媒体项目并准备播放器
                val mediaItem = MediaItem.Builder()
                    .setUri(url)
                    .build()
                simpleExoPlayer.setMediaItem(mediaItem)
                simpleExoPlayer.playWhenReady = playWhenReady
                simpleExoPlayer.seekTo(currentWindow, playPosition)
                //添加事件监听
                simpleExoPlayer.addListener(playerEventListener)
                simpleExoPlayer.prepare()
            }
    }

    /**
     * 释放播放器资源
     */
    private fun releasePlayer() {
        player?.run {
            //保存几个播放常量
            playPosition = max(0, currentPosition)
            currentWindow = currentWindowIndex
            this@ExoPlayerActivity.playWhenReady = playWhenReady
            removeListener(playerEventListener)
            release()
        }
        player = null
    }


    companion object {
        const val KEY_TYPE = "type"
        const val KEY_CURRENT_WINDOW = "c_window"
        const val KEY_PLAY_WHEN_READY = "play_when_ready"
        const val KEY_POSITION = "position"

        enum class TYPE {
            CAR_STATISTICS, WIND_STATISTICS, TURBO_STATISTICS, BLUETOOTH_HELPER, XIAO_PU, NEIGHBOR_VOICE, ENGINEERING_PLUS
        }

        @JvmStatic
        fun start(context: Context, type: TYPE) {
            val intent = Intent(context, ExoPlayerActivity::class.java)
            intent.putExtra(KEY_TYPE, type)
            context.startActivity(intent)
        }
    }

    private fun playEventListener() = object : Player.Listener {
        /**
         * 播放状态监听
         * STATE_IDLE初始状态
         * STATE_BUFFERING 加载状态
         * STATE_READY 就绪状态
         * STATE_ENDED 完毕状态
         */
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            "changed state to $stateString".logE()
        }

        /**
         * 播放错误监听
         * 与
         */
        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
        }
    }

    override fun layoutId(): Int = R.layout.activity_exo_player
    override fun initView(savedInstanceState: Bundle?) {
        val mToolbar = mViewDataBinding.mToolbar
        //初始化沉浸式
        setSupportActionBar(mViewDataBinding.mToolbar)
        supportActionBar?.title = ""
        immersionBar {
            titleBar(mToolbar)
            statusBarColor(R.color.colorTransparent)
            autoStatusBarDarkModeEnable(true)
        }
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_white)
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        mViewDataBinding.stylePlayer.setControllerVisibilityListener {
            //根据StyledPlayerControlView的显示状态，设置toolbar的返回按钮
            when (it) {
                View.VISIBLE -> {
                    mToolbar[0].visibility = View.VISIBLE
                }
                else -> {
                    mToolbar[0].visibility = View.GONE
                }
            }
        }
    }

    override fun initData() {
        type = intent.extras?.get(KEY_TYPE) as TYPE
        type?.let {
            url = when (it) {
                TYPE.CAR_STATISTICS -> "https://cdn.jsdelivr.net/gh/GuberP/mResouce/vedio/car_statistics.mp4"
                TYPE.WIND_STATISTICS -> "https://cdn.jsdelivr.net/gh/GuberP/mResouce/vedio/wind_statistics.mp4"
                TYPE.TURBO_STATISTICS -> "https://cdn.jsdelivr.net/gh/GuberP/mResouce/vedio/turbo_statistics.mp4"
                TYPE.BLUETOOTH_HELPER -> "https://cdn.jsdelivr.net/gh/GuberP/mResouce/vedio/bluetooth_hp.mp4"
                TYPE.XIAO_PU -> "https://cdn.jsdelivr.net/gh/GuberP/mResouce/vedio/xiaopu.mp4"
                TYPE.NEIGHBOR_VOICE -> "https://cdn.jsdelivr.net/gh/GuberP/mResouce/vedio/m3u8/NeighborVoice/play_list.m3u8"
                TYPE.ENGINEERING_PLUS -> "https://cdn.jsdelivr.net/gh/GuberP/mResouce/vedio/m3u8/EngineeringPulse/play_list.m3u8"
            }
        }
    }
}