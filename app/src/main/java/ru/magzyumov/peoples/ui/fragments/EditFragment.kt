package ru.magzyumov.peoples.ui.fragments

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.item_person.buttonSave
import kotlinx.android.synthetic.main.item_person.editTextEmail
import kotlinx.android.synthetic.main.item_person.editTextFirstName
import kotlinx.android.synthetic.main.item_person.editTextLastName
import ru.magzyumov.peoples.App
import ru.magzyumov.peoples.R
import ru.magzyumov.peoples.data.entity.PeopleEntity
import ru.magzyumov.peoples.ui.adapter.PeoplesAdapter
import ru.magzyumov.peoples.ui.base.BaseFragment


class EditFragment: BaseFragment() {
    override val layoutRes = R.layout.fragment_edit

    private lateinit var safeArgs: EditFragmentArgs

    init {
        App.getComponent().inject(this@EditFragment)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareSafeArgs()

        buttonSave.setOnClickListener {
            fragmentWorker.returnFragment()
        }
    }

    private fun prepareSafeArgs() {
        arguments?.let {
            safeArgs = EditFragmentArgs.fromBundle( it )
            val people = safeArgs.people
            editTextFirstName.setText(people.first_name)
            editTextLastName.setText(people.last_name)
            editTextEmail.setText(people.email)
            people.avatar?.let { avatar -> PeoplesAdapter.loadImage(imageViewAvatar, avatar) }

            fragmentWorker.changePageTitle("${people.first_name} ${people.last_name}")
        }
    }

    private fun savePeopleToDatabase() {

        if (validations()) {
            savePeople()
            fragmentWorker.showMessage(getString(R.string.people_saved))
        } else {
            fragmentWorker.showMessage(getString(R.string.people_discarded))
        }
    }

    private fun savePeople() {

        val id: Int? = arguments?.let { EditFragmentArgs.fromBundle(it).people.id }

        val people = PeopleEntity(
            id,
            editTextEmail.text.toString(),
            editTextFirstName.text.toString(),
            editTextLastName.text.toString(),
            safeArgs.people.avatar
        )

        mainViewModel.updatePeople(people)
    }

    private fun validations(): Boolean {
        return !(editTextEmail.text.isNullOrEmpty()
                && editTextLastName.text.isNullOrEmpty())
                && editTextFirstName.text.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savePeopleToDatabase()
    }

}