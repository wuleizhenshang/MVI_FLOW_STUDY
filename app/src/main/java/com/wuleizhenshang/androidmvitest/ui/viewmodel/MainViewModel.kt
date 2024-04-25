package com.wuleizhenshang.androidmvitest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuleizhenshang.androidmvitest.data.intent.MainIntent
import com.wuleizhenshang.androidmvitest.data.repository.MainRepository
import com.wuleizhenshang.androidmvitest.data.state.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/25 11:40
 * @Description: ViewModel
 */
/**
 * @link MainActivity
 */

/**
 * 这里首先创建一个意图管道，然后是一个可变的状态数据流和一个不可变观察状态数据流，观察者模式。
 * 在初始化的时候就进行意图的收集，你可以理解为监听，当收集到目标意图MainIntent.GetWallpaper时就进行相应的意图处理，
 * 调用getWallpaper()函数，这里面修改可变的状态_state，而当_state发生变化，state就观察到了，
 * 就会进行相应的动作，这个通过是在View中进行，也就是Activity/Fragment中进行。
 * 这里对_state首先赋值为Loading，表示加载中，然后进行一个网络请求，结果就是成功或者失败，
 * 如果成功，则赋值Wallpapers，View中收集到这个状态后就可以进行页面数据的渲染了，请求失败，也要更改状态。
 */
class MainViewModel(private val repository: MainRepository) : ViewModel() {

    //创建意图管道，容量无限大
    val mainIntentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    //可变状态数据流
    private val _state = MutableStateFlow<MainState>(MainState.Idle)

    //可观察状态数据流
    val state: StateFlow<MainState> get() = _state

    init {
        viewModelScope.launch {
            //收集意图
            mainIntentChannel.consumeAsFlow().collect {
                when (it) {
                    //发现意图为获取壁纸
                    is MainIntent.GetWallpaper -> getWallpaper()
                }
            }
        }
    }

    /**
     * 获取壁纸
     */
    private fun getWallpaper() {
        viewModelScope.launch {
            //修改状态为加载中
            _state.value = MainState.Loading
            //网络请求状态
            _state.value = try {
                //请求成功
                MainState.Wallpapers(repository.getWallPaper())
            } catch (e: Exception) {
                //请求失败
                MainState.Error(e.localizedMessage ?: "UnKnown Error")
            }
        }
    }
}