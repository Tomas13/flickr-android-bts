package btsdigital.kz.ui.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import btsdigital.kz.App
import btsdigital.kz.data.Repository
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    init {
        App.getApp().getmDiComponent().inject(this)
    }

    val _showErrorDialog = MutableLiveData<Event<String>>()
    val showErrorDialog: LiveData<Event<String>>
        get() = _showErrorDialog

}