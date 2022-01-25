package dima_merzlov.com.test_task_with_fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {

    lateinit var sharedPref: SharedPreferences
    val MY_PREFS_NAME = "my preferences"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        createNotificationChannel()

        var fragment: CounterFragment
        //fragment = CounterFragment.newInstance(4)
        if (sharedPref.getInt(getString(R.string.size_list_fragments), 1) == 1) {
            fragment = CounterFragment.newInstance(counter)
        } else {
            var sizeListFragments = sharedPref.getInt(getString(R.string.size_list_fragments), 0);
            fragment = CounterFragment.newInstance(counter, sizeListFragments);
        }
        listOfFragment.add(fragment)
        fragmentTransaction?.replace(R.id.fl_conteiner, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description) + " $counter"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPref = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(getString(R.string.size_list_fragments), counter)
        editor.apply()
    }

}