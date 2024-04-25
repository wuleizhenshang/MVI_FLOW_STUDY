package com.wuleizhenshang.androidmvitest.data.intent

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/25 11:30
 * @Description: 页面意图
 */

/**
 * MVI的I 是Intent，表示意图或行为，和ViewModel一样，我们在使用Intent的时候，也是一个Intent对应一个Activity/Fragment
 */
sealed class MainIntent {
    /**
     * 获取壁纸
     */
    object GetWallpaper : MainIntent()
}