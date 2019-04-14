package com.ilber.domain

import com.ilber.domain.contracts.ImageRepository
import com.ilber.domain.models.ImageHit
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImagesUC @Inject constructor(private val imageRepository: ImageRepository) {

    fun execute(query: String): Single<List<ImageHit>> =
        imageRepository.getMatchingImages(query)
}
