package com.example.callrecording

import android.Manifest
import android.app.Notification.Action
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var recorder: MediaRecorder
    private var isRecording = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()

        val callReceiver = CallReceiver(this)

        val makeCall: Button = findViewById(R.id.makeCall)

    }

    private fun hasPermissions() : Boolean{
        val recordAudioPermission = Manifest.permission.RECORD_AUDIO
        val writeToFilePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val phoneState = Manifest.permission.READ_PHONE_STATE
        return (ContextCompat.checkSelfPermission(this, recordAudioPermission) == PackageManager.PERMISSION_GRANTED
                 && ContextCompat.checkSelfPermission(this, writeToFilePermission) == PackageManager.PERMISSION_GRANTED
                 && ContextCompat.checkSelfPermission(this, phoneState) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermissions(){
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE)
        ActivityCompat.requestPermissions(this, permissions, 123)
    }

}