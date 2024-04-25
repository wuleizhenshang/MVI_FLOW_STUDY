package com.wuleizhenshang.androidmvitest.data.state

import com.wuleizhenshang.androidmvitest.data.model.Wallpaper

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/25 11:33
 * @Description: 意图状态
 */

/**
 * 一个意图有多个状态
 * intent和state就是I层
 */
sealed class MainState {
    /**
     * 空闲
     */
    object Idle : MainState()

    /**
     * 加载
     */
    object Loading : MainState()

    /**
     * 获取壁纸
     */
    data class Wallpapers(val wallpaper: Wallpaper) : MainState()

    /**
     * 错误信息
     */
    data class Error(val error: String) : MainState()
}