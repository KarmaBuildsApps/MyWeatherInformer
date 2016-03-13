package myapp.tae.ac.uk.myweatherapp.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Karma on 11/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterTest {
    private WeatherPresenter weatherPresenter;
    @Mock
    private IWeatherView view;

    @Before
    public void setUp() throws Exception {
        weatherPresenter = new WeatherPresenter(view);

    }

    @Test
    public void showErrorOnRetrofitFail() throws Exception {


    }
}