package com.battleships.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.play_btn)
    }


    fun onPlayBtn(view: View)
    {
        val i = Intent(this@MainActivity, activity_game::class.java)
        startActivity(i)
    }
}