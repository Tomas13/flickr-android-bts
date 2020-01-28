package btsdigital.kz.ui.login

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import btsdigital.kz.data.network.model.PhotoSearchResult
import btsdigital.kz.data.network.model.PhotoSearchResultItem
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import btsdigital.kz.App
import btsdigital.kz.ui.base.BaseViewModel
import btsdigital.kz.ui.base.Event
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SearchViewModel : BaseViewModel() {

    val imagesLoaded = MutableLiveData<List<PhotoSearchResultItem>>()
    val isLoading = ObservableBoolean()

    private val disposable = CompositeDisposable()

    fun requestImages() {
        isLoading.set(true)

        disposable.add(repository.listPhotos()
                .subscribeOn(Schedulers.computation())
                .doAfterTerminate { isLoading.set(false) }
                .subscribe(onSuccess(), onError()))
    }

    fun requestImages(query: String) {
        isLoading.set(true)

        disposable.add(repository.searchPhotos(query)
                .subscribeOn(Schedulers.computation())
                .doAfterTerminate { isLoading.set(false) }
                .subscribe(onSuccess(), onError()))
    }

    private fun onSuccess(): Consumer<PhotoSearchResult> {
        return Consumer { photoSearchResult ->
            imagesLoaded.postValue(photoSearchResult.items)
        }
    }

    private fun onError(): Consumer<Throwable> {
        return Consumer { throwable ->
            _showErrorDialog.postValue(Event(throwable.message.toString()))
        }
    }

    fun saveSearch(text: String) {
        disposable.add(Single.just(text)
                .subscribeOn(Schedulers.io())
                .subscribe { value ->
                    repository.saveSuggestions(value)
                })
    }

    fun loadSuggestions(): List<String> {
        val results = ArrayList<String>()
        disposable.add(repository.getSuggestions()
                .subscribeOn(Schedulers.io())
                .subscribe { items ->
                    for (item in items) {
                        results.add(item.suggestion)
                    }
                })
        return results
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("is cleared SVM")
        disposable.dispose()
    }
}
