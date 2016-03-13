package myapp.tae.ac.uk.myweatherapp;

import android.app.Application;

import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.di.components.APIComponent;
import myapp.tae.ac.uk.myweatherapp.di.components.DaggerAPIComponent;
import myapp.tae.ac.uk.myweatherapp.di.components.DaggerNetComponent;
import myapp.tae.ac.uk.myweatherapp.di.components.NetComponent;
import myapp.tae.ac.uk.myweatherapp.di.modules.APIModule;
import myapp.tae.ac.uk.myweatherapp.di.modules.AppModule;
import myapp.tae.ac.uk.myweatherapp.di.modules.NetModule;

/**
 * Created by Karma on 11/03/16.
 */
public class MyApplication extends Application {
    private APIComponent mAPIComponent;
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder().
                netModule(new NetModule(Constants.BASE_WEATHER_URL)).
                appModule(new AppModule(this)).build();
        mAPIComponent = DaggerAPIComponent.builder().
                aPIModule(new APIModule()).
                netComponent(mNetComponent).
                build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public APIComponent getApiComponent() {
        return mAPIComponent;
    }
}
