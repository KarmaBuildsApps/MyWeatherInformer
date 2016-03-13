package myapp.tae.ac.uk.myweatherapp.di.components;

import dagger.Component;
import myapp.tae.ac.uk.myweatherapp.MainActivity;
import myapp.tae.ac.uk.myweatherapp.di.modules.APIModule;
import myapp.tae.ac.uk.myweatherapp.di.scopes.Scopes;

/**
 * Created by Karma on 11/03/16.
 */
@Scopes
@Component(dependencies = NetComponent.class, modules = APIModule.class)
public interface APIComponent {
    void inject(MainActivity mainActivity);
}
