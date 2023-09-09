package com.example.callrecording

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.telephony.TelephonyManager
import android.util.Log
import java.io.File
import java.io.IOException

class CallReceiver(private val context : Context) : BroadcastReceiver() {
    private var isRecording = false;
    private lateinit var mediaRecorder : MediaRecorder

    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)
        when(state){
            TelephonyManager.EXTRA_STATE_RINGING -> {
                startRecording()
            }

            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                startRecording()
            }

            TelephonyManager.EXTRA_STATE_IDLE ->{
                stopRecording()
            }
        }

    }


     private fun startRecording() {
         mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MediaRecorder(context) else MediaRecorder()
         if(!isRecording){
         mediaRecorder.apply {
             setAudioSource(MediaRecorder.AudioSource.MIC)
             setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
             setOutputFile(getRecordingFilePath())
             setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) }
             try {
                 mediaRecorder.prepare()
                 mediaRecorder.start()
             } catch (e : IOException){
                 Log.d("CallRec", e.toString())
             }
         }
         isRecording = true
    }

    private fun getRecordingFilePath(): String {
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        val filePath = File(dir, "testing_${System.currentTimeMillis()}" + ".mp3")
        return filePath.path
    }

    private fun stopRecording(){
       if(isRecording){
           mediaRecorder.stop()
           mediaRecorder.reset()
           mediaRecorder.reset()
           isRecording = false
       }
    }

}