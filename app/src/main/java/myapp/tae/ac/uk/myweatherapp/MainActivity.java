package myapp.tae.ac.uk.myweatherapp;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myweatherapp.api.IWeatherAPI;
import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.model.WeatherInfo;
import myapp.tae.ac.uk.myweatherapp.model.Wind;
import myapp.tae.ac.uk.myweatherapp.presenter.IWeatherView;
import myapp.tae.ac.uk.myweatherapp.presenter.WeatherPresenter;
import myapp.tae.ac.uk.myweatherapp.ui.AdapterViewPager;
import myapp.tae.ac.uk.myweatherapp.ui.FragmentTodayWeather;
import myapp.tae.ac.uk.myweatherapp.ui.FragmentWindIfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IWeatherView {
    @Inject
    IWeatherAPI mIWeatherAPI;
    @Bind(R.id.vpPager)
    ViewPager viewPager;
    @Bind(R.id.weatherToolbar)
    Toolbar toolbar;
    private AdapterViewPager adapter;

    private ProgressDialog progressDialog;
    private WeatherPresenter weatherPresenter;
    private WeatherInfo weatherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpInjections();
        setUpToolbar();
        setUpProgressDialog();
        weatherPresenter = new WeatherPresenter(this, mIWeatherAPI);
        adapter = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        loadWeather(Constants.DEFAULT_CITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
    }

    private void setUpInjections() {
        ((MyApplication) getApplication()).getApiComponent().inject(this);
        ButterKnife.bind(this);
    }


    @Override
    public void loadWeather(String city) {
        progressDialog.show();
        weatherPresenter.loadData(city);
    }

    @Override
    public IWeatherAPI getWeatherAPI() {
        return mIWeatherAPI;
    }

    @Override
    public void onSearchButtonClicked(View view) {
    }

    @Override
    public String getSearchText() {
        return null;
    }

    @Override
    public void updateWeatherViews(WeatherInfo weatherInfo) {
        ((FragmentTodayWeather) adapter.getItem(Constants.WEATHER_TODAY_FRAGMENT)).updateViews(weatherInfo);
        Wind wind = weatherInfo.getWind();
        ((FragmentWindIfo) adapter.getItem(Constants.WIND_TODAY_FRAGMENT)).updateViews(wind);
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void showSearchEmptyError(int redId) {

    }
}
