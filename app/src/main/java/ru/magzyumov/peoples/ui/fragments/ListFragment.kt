package ru.magzyumov.peoples.ui.fragments


import ru.magzyumov.peoples.ui.adapter.PeoplesAdapter

import android.os.Bundle

import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*


import ru.magzyumov.peoples.App
import ru.magzyumov.peoples.R
import ru.magzyumov.peoples.data.entity.PeopleEntity
import ru.magzyumov.peoples.ui.base.BaseFragment
import javax.inject.Inject


class ListFragment: BaseFragment(), PeoplesAdapter.Interaction {

    override val layoutRes = R.layout.fragment_list

    private lateinit var peoplesAdapter: PeoplesAdapter
    private lateinit var allPeoples: List<PeopleEntity>

    init {
        App.getComponent().inject(this@ListFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        fragmentWorker.changePageTitle(getString(R.string.app_name))

        allPeoples = arrayListOf()

        initRecyclerView()
        observerLiveData()
    }

    private fun observerLiveData() {
        mainViewModel.getPeoplesFromDB().observe(viewLifecycleOwner, Observer { listOfPeoples ->
            listOfPeoples?.let {
                allPeoples = listOfPeoples
                peoplesAdapter.swap(it)
            }
        })
    }

    private fun initRecyclerView() {
        recyclerViewPeoples.apply {
            peoplesAdapter = PeoplesAdapter(
                allPeoples,
                this@ListFragment
            )
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = peoplesAdapter
            val swipe = ItemTouchHelper(initSwipeToDelete())
            swipe.attachToRecyclerView(recyclerViewPeoples)
        }
    }

    private fun initSwipeToDelete(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                this@ListFragment.mainViewModel.deletePeople(allPeoples[position])
                fragmentWorker.showMessage(getString(R.string.people_deleted))
            }
        }
    }

    override fun onItemSelected(position: Int, item: PeopleEntity) {
        val navDirection = ListFragmentDirections.actionListFragmentToEditFragment(item)
        findNavController().navigate(navDirection)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuActionUpdate -> {mainViewModel.getPeoplesFromServer()}
        }
        return super.onOptionsItemSelected(item)
    }
}