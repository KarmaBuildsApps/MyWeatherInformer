package myapp.tae.ac.uk.myweatherapp.api;

import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.model.WeatherInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Karma on 11/03/16.
 */
public interface IWeatherAPI {
    @GET(Constants.WEATHER_QUERY)
    Call<WeatherInfo> getWeatherInfo(@Query("q") String cityName);
}
