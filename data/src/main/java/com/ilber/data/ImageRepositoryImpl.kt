package com.ilber.data

import com.ilber.data.models.PixabayResponse
import com.ilber.domain.contracts.ImageRepository
import com.ilber.domain.models.ImageHit
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepositoryImpl @Inject constructor(private val retrofit: Retrofit) : ImageRepository {
    companion object {
        private const val ApiKey = "<INSERT YOUR API KEY>"
        private const val PhotoSearchTerm = "photo"
    }

    override fun getMatchingImages(queryTerm: String): Single<List<ImageHit>> =
        retrofit.create(PixabayApiService::class.java)
            .queryPhotos(ApiKey, queryTerm, PhotoSearchTerm)
            .map { result ->
                result.hits.map { hit -> hit.toImageHit() }
            }

    private fun PixabayResponse.Hit.toImageHit(): ImageHit =
        ImageHit(this.user, this.previewURL.orEmpty(), this.tags.orEmpty())

    private interface PixabayApiService {
        @GET("?")
        fun queryPhotos(
            @Query("key") key: String,
            @Query("q") query: String,
            @Query("image_type") what: String
        ): Single<PixabayResponse>
    }
}