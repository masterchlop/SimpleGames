package com.example.pong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.room.Room
import com.example.pong.data.AppDatabase
import com.example.pong.data.Score
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

private lateinit var database:AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "score2.db"
        ).allowMainThreadQueries().build()


        setContentView(R.layout.activity_main)
        var b=database.ScoreDAO().getAllbounces().toString()
        if(b=="0"){
            database.ScoreDAO().insert2()
        }
        b= database.ScoreDAO().naj().toString()




        findViewById<TextView>(R.id.textView2).text=b
        val button = findViewById<Button>(R.id.PlayB) as Button
        button.setOnClickListener{
            val intent = Intent(this, GameClass::class.java)
            startActivity(intent)
        }    }




}