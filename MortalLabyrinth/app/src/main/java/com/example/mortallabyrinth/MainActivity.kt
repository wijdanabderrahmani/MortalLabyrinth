package com.example.mortallabyrinth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mazeView = MazeView(this)
        setContentView(mazeView)
    }
}
