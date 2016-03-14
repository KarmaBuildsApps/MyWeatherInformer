package myapp.tae.ac.uk.myweatherapp.presenter;

import android.view.View;

import myapp.tae.ac.uk.myweatherapp.api.IWeatherAPI;
import myapp.tae.ac.uk.myweatherapp.model.weather.WeatherInfo;

/**
 * Created by Karma on 11/03/16.
 */
public interface IWeatherView {
    public void loadWeather(String city);

    public IWeatherAPI getWeatherAPI();

    public void onSearchButtonClicked(View view);

    String getSearchText();

    void updateWeatherViews(WeatherInfo weatherInfo);

    void dismissProgressDialog();

    void showSearchEmptyError(int redId);
}
