package ru.magzyumov.peoples.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.magzyumov.peoples.App
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var mainViewModel: MainViewModel

    init {
        App.getComponent().inject(this@SplashActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataFromServer()

        closeSplash()
    }

    private fun closeSplash() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getDataFromServer(){
        mainViewModel.getPeoplesFromServer()
    }
}