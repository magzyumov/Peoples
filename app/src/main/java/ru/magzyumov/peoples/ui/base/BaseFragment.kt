package ru.magzyumov.peoples.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.magzyumov.peoples.ui.main.FragmentWorker
import ru.magzyumov.peoples.ui.main.MainViewModel
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    abstract val layoutRes: Int
    protected lateinit var fragmentWorker: FragmentWorker


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentWorker) fragmentWorker = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

}