package dima_merzlov.com.test_task_with_fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CounterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
var counter = 1;
var coountCreatedFragment = 0
const val CHANEL_ID = "chanel id"
var listOfFragment = mutableListOf<CounterFragment>()
var fragment: CounterFragment? = null
val MY_PREFS_NAME = "my preferences"

class CounterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = 0
    private var param2: Int? = 0
    lateinit var sharedPref:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPref= this.activity?.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE) ?: return null
        val view: View = inflater.inflate(R.layout.fragment_counter, container, false)
        if (param1 != 0) {
            var counter = view.findViewById<TextView>(R.id.tv_counter)
            counter.text = param1.toString()
        }
        if (param2 != 0) {
            while (coountCreatedFragment != param2) {
                createNewFragment()

            }
        }

        val buttonPlus = view.findViewById<TextView>(R.id.tv_plus)
        buttonPlus.setOnClickListener {
            createNewFragment()
        }
        val buttonMinus = view.findViewById<TextView>(R.id.tv_minus)
        buttonMinus.setOnClickListener {
            counter--
            requireActivity().onBackPressed()

        }
        val intent = Intent(requireActivity(), MainActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(requireContext()).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = view.findViewById<TextView>(R.id.tv_notifications)
        notification.setOnClickListener {
            val notification = NotificationCompat.Builder(requireContext(), CHANEL_ID)
                .setContentTitle("title $counter")
                .setContentIntent(pendingIntent)
                .setContentText("text")
                .setSmallIcon(R.drawable.ic_baseline_exposure_plus_1_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
            val notificationManager = NotificationManagerCompat.from(requireContext())
            notificationManager.notify(counter, notification)
            saveToShared()
        }
        return view
    }

    private fun createNewFragment() {
        val fragmentManager = this.fragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        counter++
        coountCreatedFragment = counter
         fragment = CounterFragment.newInstance(counter)
        listOfFragment.add(fragment!!)
        fragmentTransaction?.replace(R.id.fl_conteiner, fragment!!)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()

    }

    private fun saveToShared(){
        Log.d("My message", "in on destroy $counter")
        val editor = sharedPref.edit()
        editor.putInt(getString(R.string.size_list_fragments), counter)
        editor.apply()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CounterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }

        fun newInstance(param1: Int, param2: Int) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }


}