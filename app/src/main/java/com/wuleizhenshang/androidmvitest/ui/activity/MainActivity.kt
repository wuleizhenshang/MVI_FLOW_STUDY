package com.wuleizhenshang.androidmvitest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.wuleizhenshang.androidmvitest.network.NetworkUtils
import com.wuleizhenshang.androidmvitest.databinding.ActivityMainBinding
import com.wuleizhenshang.androidmvitest.data.intent.MainIntent
import com.wuleizhenshang.androidmvitest.data.state.MainState
import com.wuleizhenshang.androidmvitest.ui.adapter.WallpaperAdapter
import com.wuleizhenshang.androidmvitest.ui.viewmodel.MainViewModel
import com.wuleizhenshang.androidmvitest.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

/**
 * 说明一下，首先声明变量并在onCreate()中进行初始化，这里绑定ViewModel采用的是ViewModelProvider()，
 * 而不是ViewModelProviders.of，这是因为这个API已经被移除了，在之前的版本中是过时弃用，
 * 在最新的版本中你都找不到这个API了，所以使用ViewModelProvider()，
 * 然后通过ViewModelFactory去创建对应的MainViewModel。
 *
 * initView()函数中是控件的一些配置，比如给RecyclerView添加布局管理器和设置适配器，给按钮添加点击事件，
 * 在点击的时候发送意图，发送的意图被MainViewModel中mainIntentChannel收集到，然后执行网络请求操作，
 * 此时意图的状态为Loading。
 *
 *
 * observeViewModel()函数中是对状态的收集，在状态为Loading，隐藏按钮，显示加载条，然后网络请求会有结果，
 * 如果是成功，则在UI上隐藏按钮和加载条，显示列表控件，并添加数据到适配器中，然后刷新适配器，数据就会渲染出来；
 * 如果是失败则显示按钮，隐藏加载条，打印错误信息并提示一下。
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

    private var wallPaperAdapter = WallpaperAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //使用ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //绑定ViewModel
        mainViewModel = ViewModelProvider(this, ViewModelFactory(NetworkUtils.apiService))[MainViewModel::class.java]
        //初始化
        initView()
        //观察ViewModel
        observeViewModel()
    }

    /**
     * 观察ViewModel
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            //状态收集
            mainViewModel.state.collect {
                when(it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        binding.btnGetWallpaper.visibility = View.GONE
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    is MainState.Wallpapers -> {     //数据返回
                        binding.btnGetWallpaper.visibility = View.GONE
                        binding.pbLoading.visibility = View.GONE

                        binding.rvWallpaper.visibility = View.VISIBLE
                        it.wallpaper.let { paper ->
                            wallPaperAdapter.addData(paper.res.vertical)
                        }
                        wallPaperAdapter.notifyDataSetChanged()
                    }
                    is MainState.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.btnGetWallpaper.visibility = View.VISIBLE
                        Log.d("TAG", "observeViewModel: $it.error")
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    /**
     * 初始化
     */
    private fun initView() {
        //RV配置
        binding.rvWallpaper.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter  = wallPaperAdapter
        }
        //按钮点击
        binding.btnGetWallpaper.setOnClickListener {
            lifecycleScope.launch{
                //发送意图
                mainViewModel.mainIntentChannel.send(MainIntent.GetWallpaper)
            }
        }
    }
}
