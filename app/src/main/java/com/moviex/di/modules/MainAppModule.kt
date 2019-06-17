package com.moviex.di.modules

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moviex.R
import com.moviex.common.IResourceProvider
import com.moviex.di.factory.ViewModelFactory
import com.moviex.di.qualifiers.ApiKeyQualifier
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class MainAppModule {

    @Binds
    internal abstract fun provideContext(application: Application): Context

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @Reusable
        @JvmStatic
        fun resourceProvider(context: Context): IResourceProvider {
            return object : IResourceProvider {
                override fun getString(id: Int, vararg args: Any?): String = context.getString(id, *args)

                override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)

                override fun getString(id: Int): String = context.getString(id)

                override fun getQuantityString(stringRes: Int, quantity: Int, vararg formatArgs: Any): String {
                    return context.resources.getQuantityString(stringRes, quantity, *formatArgs)
                }

                override fun getFontTypeFace(id: Int): Typeface? = ResourcesCompat.getFont(context, id)

                override fun getColor(colorRes: Int): Int = ContextCompat.getColor(context, colorRes)

                override fun getDrawable(drawableRes: Int): Drawable {
                    return requireNotNull(ContextCompat.getDrawable(context, drawableRes))
                }
            }
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providesGson(): Gson {
            return GsonBuilder().create()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providesOkHttp(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            return httpClient.build()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun retrofit(client: OkHttpClient, resourceProvider: IResourceProvider): Retrofit {
            return Retrofit.Builder()
                    .client(client)
                    .baseUrl(resourceProvider.getString(R.string.base_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        @Provides
        @JvmStatic
        @ApiKeyQualifier
        fun provideApiKey(resourceProvider: IResourceProvider): String {
            return resourceProvider.getString(R.string.api_key)
        }
    }
}