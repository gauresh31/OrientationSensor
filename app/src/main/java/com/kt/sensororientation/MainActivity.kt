package com.kt.sensororientation

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.OrientationEventListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    private val TAG: String? = "SensorActivity"
    var sensorManager : SensorManager? = null
    private val landscapeRoll: Boolean = false
    private var landscape: Boolean = false
    private var portrait: Boolean = false
    private var portraitRoll: Boolean = false
    private var landscapePitch: Boolean = false
    private var portraitPitch: Boolean = false
    lateinit var tvSensor : TextView
    var orientationEventListener : OrientationEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager!!.registerListener(this,
                sensorManager!!.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_NORMAL)
        tvSensor = findViewById(R.id.tv_or_data)
        orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                Log.d(TAG, "orientation = $orientation")
            }
        }
        (orientationEventListener as OrientationEventListener).enable()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val values: FloatArray = event?.values!!

        val pitch = values[1]
        val roll = values[2]

        if (-110 <= pitch && pitch <= -70 || pitch in 70.0..110.0) {
            //PORTRAIT MODE
            portraitPitch = true
            landscapePitch = false
            Log.d(TAG, "portrait mode: pitch = $pitch")
        } else if (-20 <= pitch && pitch <= 20 || -200 <= pitch && pitch <= -160 || pitch in 160.0..200.0) {
            //LANDSCAPE MODE
            portraitPitch = false
            landscapePitch = true
            Log.d(TAG, "landscape mode : pitch = $pitch")
        }

        if (-20 <= roll && roll <= 20) {
            //PORTRAIT MODE
            portraitRoll = true
            landscapePitch = false
            Log.d(TAG, "portrait mode: roll = $roll")
        } else if (-110 <= roll && roll <= -70 || roll in 70.0..110.0) {
            //LANDSCAPE MODE
            portraitRoll = false
            landscapePitch = true
            Log.d(TAG, "landscape mode : roll = $roll")
        }

        if (portraitPitch && portraitRoll && !portrait) {
            portrait = true
            landscape = false
            tvSensor.text = "portrait mode for icons: pitch = $pitch, roll = $roll"
            Log.d(TAG, "portrait mode for icons: pitch = $pitch, roll = $roll")
        }

        if (landscapePitch && landscapeRoll && !landscape) {
            landscape = true
            portrait = false
            tvSensor.text = "landscape mode for icons: pitch = $pitch, roll = $roll"
            Log.d(TAG, "landscape mode for icons: pitch = $pitch, roll = $roll")
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}