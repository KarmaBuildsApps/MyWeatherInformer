package myapp.tae.ac.uk.myweatherapp.di.modules;

import dagger.Module;
import dagger.Provides;
import myapp.tae.ac.uk.myweatherapp.api.IWeatherAPI;
import myapp.tae.ac.uk.myweatherapp.di.scopes.Scopes;
import retrofit2.Retrofit;

/**
 * Created by Karma on 11/03/16.
 */
@Module
public class APIModule {
    @Scopes
    @Provides
    IWeatherAPI provideIWeatherAPI(Retrofit retrofit) {
        return retrofit.create(IWeatherAPI.class);
    }
}
