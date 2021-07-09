
package com.example.countdowntimer

import android.app.TimePickerDialog
import android.icu.util.TimeUnit
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*

class MainActivity : AppCompatActivity() {



    companion object{
        private const val TAG = "MainActivity"
        lateinit var countDownTimer: CountDownTimer

    }

    private var  tv_timer :TextView?=null
    private var btnstartStop : Button?=null
    private var isStart=false;
    private var mEdittext_input : TextView?=null
    private var millisinput : Long?=null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mEdittext_input=findViewById(R.id.input)
        chose_time()
        initviews();






    }

    private fun initviews(){
        tv_timer=findViewById(R.id.time)
        btnstartStop=findViewById(R.id.set_time)
        btnstartStop?.setOnClickListener{
            if (!isStart){

                startTimer()
            }else{
                stopTimer()
            }
        }
    }






    @RequiresApi(Build.VERSION_CODES.O)
    private fun startTimer(){

        var input= mEdittext_input?.getText().toString()
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())



        if (input.length==0){

             Toast.makeText(this, "Filed cant be empty", Toast.LENGTH_SHORT).show()

        }
        else{
//            val start: LocalTime = LocalTime.parse("${input}")
//            val stop: LocalTime = LocalTime.parse("${currentTime}")
//            val MINUTES_: Long = ChronoUnit.MINUTES.between(stop, start)
//            Log.d(TAG, "cal::: ${MINUTES_}")

            millisinput=input.toLong()*60000
            countDownTimer = object  : CountDownTimer(millisinput!!,1000){
                override fun onTick(millisUntilFinished: Long) {

                    var hours=(millisUntilFinished/1000)/3600
                    var minutes=((millisUntilFinished/1000)%3600)/60
                    var seconds=(millisUntilFinished/1000)%60

                    if (hours>0){
                        var timeLeftFormated=String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds)
                        Log.d(TAG, "onTick: ${timeLeftFormated}")

                        //update text
                        tv_timer?.text=timeLeftFormated

                    }

                }

                override fun onFinish() {
                    isStart=false
                    btnstartStop?.text="Start"
                }

            }.start()

            isStart=true
            btnstartStop?.text="Stop"
        }
//        if (millisinput==0.toLong()){
//
//        }
    }

    private fun stopTimer(){
        countDownTimer.cancel()
        isStart=false
        btnstartStop?.text="Start"
        tv_timer?.text="00.00"

    }

    override fun onDestroy() {
        super.onDestroy()
      countDownTimer.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chose_time(){
        mEdittext_input?.setOnClickListener{

            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
               var time = SimpleDateFormat("HH:mm").format(cal.time)
                System.out.println(" time ::: "+time)
                var currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                val start: LocalTime = LocalTime.parse("${time}")
                val stop: LocalTime = LocalTime.parse("${currentTime}")
                val MINUTES_: Long = ChronoUnit.MINUTES.between(stop, start)

                mEdittext_input?.text= MINUTES_.toString()

            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }
}