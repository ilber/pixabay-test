package com.ilber.pixabay.views.search

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilber.pixabay.R
import com.ilber.pixabay.di.factories.ViewModelFactory
import com.ilber.pixabay.views.search.adapter.SearchResultAdapter
import com.jakewharton.rxbinding2.widget.textChanges
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.search_result
import kotlinx.android.synthetic.main.activity_main.search_term_edit_text
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val searchViewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    private var textChangesDisposable: Disposable? = null
    private var searchDisposable: Disposable? = null

    private val searchResultAdapter = SearchResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        search_result.layoutManager = LinearLayoutManager(this)
        search_result.adapter = searchResultAdapter

        textChangesDisposable = search_term_edit_text.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeBy {
                if(it.isBlank()) return@subscribeBy

                searchDisposable?.dispose()
                searchDisposable = searchViewModel.getMatchingImages(it.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(onError = ::handleError, onSuccess = ::handleNextItem)
            }
    }

    override fun onDestroy() {
        textChangesDisposable?.dispose()
        searchDisposable?.dispose()

        super.onDestroy()
    }

    private fun handleNextItem(result: List<SearchViewModel.SearchResult>) {
        Log.i("HERE", "Total images ${result.count()}")
        searchResultAdapter.updateResults(result)
    }

    private fun handleError(throwable: Throwable) {
        Log.e("HERE", throwable.message)
    }
}
