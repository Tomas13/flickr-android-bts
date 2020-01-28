package btsdigital.kz.di

import android.app.Application
import android.content.Context
import btsdigital.kz.data.Repo
import btsdigital.kz.data.Repository
import btsdigital.kz.data.db.SuggestionsDatabase
import btsdigital.kz.data.network.ApiHelper
import btsdigital.kz.data.network.AppApiHelper
import btsdigital.kz.data.network.NetworkService
import btsdigital.kz.utils.AppConstants
import btsdigital.kz.utils.AppConstants.BASE_URL
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import malmalimet.kz.data.db.SuggestionsDao
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DiModule(private val mApplication: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    @Singleton
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }


    @Provides
    @Singleton
    fun provideRoomDb(context: Context): SuggestionsDao {
        return SuggestionsDatabase.getInstance(context).suggestionsDao()
    }

    @Provides
    @Singleton
    fun provideRepository(repository: Repository): Repo {
        return repository
    }

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: Cache?): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addNetworkInterceptor(StethoInterceptor())
        client.connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(okHttpClient: OkHttpClient): NetworkService {
        val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
        return retrofit.create(NetworkService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

}