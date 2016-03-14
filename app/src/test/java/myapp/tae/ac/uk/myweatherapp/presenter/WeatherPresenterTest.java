package myapp.tae.ac.uk.myweatherapp.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import myapp.tae.ac.uk.myweatherapp.api.IWeatherAPI;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Karma on 11/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {
    private WeatherPresenter weatherPresenter;
    @Mock
    private IWeatherView view;
    @Mock
    IWeatherAPI api;

    @Before
    public void setUp() throws Exception {
        weatherPresenter = new WeatherPresenter(view, api);

    }

    @Test
    public void showErrorOnRetrofitFail() throws Exception {
//        when(view.loadWeather("London"))

    }
}