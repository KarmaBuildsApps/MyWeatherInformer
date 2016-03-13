package myapp.tae.ac.uk.myweatherapp.presenter;

import android.util.Log;

import myapp.tae.ac.uk.myweatherapp.R;
import myapp.tae.ac.uk.myweatherapp.api.IWeatherAPI;
import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.model.WeatherInfo;
import myapp.tae.ac.uk.myweatherapp.ui.AdapterViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Karma on 11/03/16.
 */
public class WeatherPresenter {
    private IWeatherView view;
    private IWeatherAPI mIWeatherAPI;

    public WeatherPresenter(IWeatherView view, IWeatherAPI weatherAPI) {
        this.view = view;
        this.mIWeatherAPI = weatherAPI;
    }

    public void loadData(String cityName) {
        if (cityName.isEmpty()) {
            view.showSearchEmptyError(R.string.error_search_empty_text);
        }
        mIWeatherAPI.getWeatherInfo(cityName).enqueue(new Callback<WeatherInfo>() {
            @Override
            public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
                view.dismissProgressDialog();
                updateWeatherViews(response.body());
            }

            @Override
            public void onFailure(Call<WeatherInfo> call, Throwable t) {
                view.dismissProgressDialog();
                Log.i("IWeatherAPI", "onFailure: " + t.getMessage());
            }
        });
    }

    public void updateWeatherViews(WeatherInfo weatherInfo) {
        view.updateWeatherViews(weatherInfo);
    }
}
