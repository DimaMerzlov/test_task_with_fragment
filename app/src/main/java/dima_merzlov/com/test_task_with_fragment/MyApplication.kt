package dima_merzlov.com.test_task_with_fragment

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        counter=0
    }
}