package cn.rubintry.runtimemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun print(view: android.view.View) {
        startActivity(Intent(this , MainActivity2::class.java))
    }
}