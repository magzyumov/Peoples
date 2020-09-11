package ru.magzyumov.peoples.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.magzyumov.peoples.R

class MainActivity : AppCompatActivity(), FragmentWorker {
    private lateinit var navController: NavController
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        rootView = findViewById(R.id.nav_host_fragment)
    }

    override fun changePageTitle(title: String) {
        toolbar.title = title
    }

    override fun returnFragment(){
        navController.popBackStack()
    }

    override fun showMessage(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}