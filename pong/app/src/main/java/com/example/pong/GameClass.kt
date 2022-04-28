package com.example.pong

import android.content.Context

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.pong.data.AppDatabase
import com.example.pong.data.Score
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GameClass: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)


        setContentView(R.layout.activity_game)

        }










}












