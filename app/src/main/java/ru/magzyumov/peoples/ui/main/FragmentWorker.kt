package ru.magzyumov.peoples.ui.main

interface FragmentWorker {
    fun changePageTitle(title: String)
    fun returnFragment()
    fun showMessage(message: String)
}