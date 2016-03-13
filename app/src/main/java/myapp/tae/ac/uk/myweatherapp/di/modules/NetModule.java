package myapp.tae.ac.uk.myweatherapp.di.modules;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Karma on 11/03/16.
 */
@Module
public class NetModule {
    String base_url;

    public NetModule(String base_url) {
        this.base_url = base_url;
    }

    @Singleton
    @Provides
    Cache provideOkHttpCache(Application application) {
        long cacheSize = 10 * 1024 * 1024;
        Cache okHttpCache = null;
        try {
            okHttpCache = new Cache(application.getCacheDir(), cacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return okHttpCache;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        return gson;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient.addInterceptor(interceptor);
        return okHttpClient.build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit restAdapter = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return restAdapter;

    }
}
