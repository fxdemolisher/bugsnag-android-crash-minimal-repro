package com.thirdtimetech.bugsnagandroidcrashminimalrepro

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var running: Boolean = false
    private lateinit var statusView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusView = findViewById(R.id.status)
    }

    override fun onResume() {
        super.onResume()

        Thread {
            running = true
            while(running) {
                doRequest()
                Thread.sleep(5000)
            }

            setMessage("Stopping requests")
        }.start()
    }

    override fun onPause() {
        super.onPause()

        running = false
    }

    private fun doRequest() {
        try {
            setMessage("Starting request")

            val connection: HttpURLConnection = URL("https://httpstat.us/200?sleep=3000")
                .openConnection() as HttpURLConnection

            connection.useCaches = false
            connection.doOutput = false
            connection.doInput = true

            val reader = InputStreamReader(connection.getInputStream())
            val data = reader.readText()
            reader.close()
            connection.disconnect()

            setMessage("Received data and pausing for 5 seconds:\n\n" + data);
        } catch(e: Throwable) {
            Log.e("ERROR", "Error occurred", e)
            setMessage("Error occurred: " + e.message)
        }
    }

    private fun setMessage(msg: String) {
        runOnUiThread {
            statusView.text = msg
        }
    }
}
