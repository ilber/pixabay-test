package com.ilber.domain.contracts

import com.ilber.domain.models.ImageHit
import io.reactivex.Single

interface ImageRepository {
    fun getMatchingImages(queryTerm: String): Single<List<ImageHit>>
}