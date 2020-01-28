package btsdigital.kz.di

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.Component
import btsdigital.kz.App
import btsdigital.kz.data.Repository
import btsdigital.kz.data.network.NetworkService
import btsdigital.kz.ui.MainActivity
import btsdigital.kz.ui.base.BaseViewModel
import btsdigital.kz.ui.login.SearchViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [DiModule::class])
interface DiComponent {
    fun inject(app: App)
    fun context(): Context
    val repository: Repository
    fun application(): Application
    val networkService: NetworkService

    fun inject(scanActivity: Activity)
    fun inject(scanActivity: MainActivity)
    fun inject(baseViewModel: BaseViewModel)
    fun inject(searchViewModel: SearchViewModel)
}