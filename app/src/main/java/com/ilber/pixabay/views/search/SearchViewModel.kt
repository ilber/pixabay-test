package com.ilber.pixabay.views.search

import androidx.lifecycle.ViewModel
import com.ilber.domain.SearchImagesUC
import io.reactivex.Single
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchImagesUC: SearchImagesUC) : ViewModel() {
    fun getMatchingImages(searchTerm: String): Single<List<SearchResult>> =
        searchImagesUC.execute(searchTerm)
            .map { result ->
                result.map { imageHit ->
                    SearchResult(
                        imageHit.url,
                        imageHit.creator,
                        imageHit.tags
                    )
                }
            }

    data class SearchResult(
        val previewUrl: String,
        val user: String,
        val tags: String
    )
}