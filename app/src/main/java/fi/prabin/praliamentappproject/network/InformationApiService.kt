package fi.prabin.praliamentappproject.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fi.prabin.praliamentappproject.database.ParliamentMemberInfo
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://users.metropolia.fi/~peterh/"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getList] method
 */
interface InformationApiService {
    @GET("seating.json")
    suspend fun getList(): List<ParliamentMemberInfo>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MemberInformationApi {
    val retrofitService : InformationApiService by lazy {
        retrofit.create(InformationApiService::class.java) }
}