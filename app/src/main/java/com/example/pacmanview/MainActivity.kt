package com.example.pacmanview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pacmanview.views.PacmanView

class MainActivity : AppCompatActivity() {

    private val btnStart by lazy { findViewById<Button>(R.id.btnStart) }
    private val pacman by lazy { findViewById<PacmanView>(R.id.pacman) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        btnStart.setOnClickListener {
            pacman.startAnimation()
        }
    }
}