package ru.magzyumov.peoples.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.magzyumov.peoples.App
import ru.magzyumov.peoples.R
import javax.inject.Inject

class MainActivity : AppCompatActivity(), FragmentWorker {
    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var navController: NavController
    private lateinit var rootView: View

    init {
        App.getComponent().inject(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)

        rootView = findViewById(R.id.nav_host_fragment)

        mainViewModel.getNetWorkStatus().observe(this, Observer { status ->
            showMessage(status)
        })
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