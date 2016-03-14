package myapp.tae.ac.uk.myweatherapp;

import android.app.ProgressDialog;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myweatherapp.api.IWeatherAPI;
import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.model.weather.WeatherInfo;
import myapp.tae.ac.uk.myweatherapp.model.weather.Wind;
import myapp.tae.ac.uk.myweatherapp.presenter.IWeatherView;
import myapp.tae.ac.uk.myweatherapp.presenter.WeatherPresenter;
import myapp.tae.ac.uk.myweatherapp.ui.adapters.AdapterArrayPlace;
import myapp.tae.ac.uk.myweatherapp.ui.adapters.AdapterViewPager;
import myapp.tae.ac.uk.myweatherapp.ui.fragments.FragmentTodayWeather;
import myapp.tae.ac.uk.myweatherapp.ui.fragments.FragmentWindIfo;

public class MainActivity extends AppCompatActivity implements IWeatherView {
    @Inject
    IWeatherAPI mIWeatherAPI;
    @Bind(R.id.vpPager)
    ViewPager viewPager;
    //    @Bind(R.id.weatherToolbar)
//    AutoCompleteTextView autoCompleteSearch;
    SupportPlaceAutocompleteFragment autocompleteFragment;
    private String mCountryCode;
    private String mCityName;

    private AdapterViewPager adapter;
    private ProgressDialog progressDialog;
    private WeatherPresenter weatherPresenter;
    private WeatherInfo weatherInfo;
    private AdapterArrayPlace placeAdapter;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpInjections();
//        setUpToolbar();
        setUpProgressDialog();
        setUpAutoCompleteFragment();
        weatherPresenter = new WeatherPresenter(this, mIWeatherAPI);
        adapter = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        loadWeather(Constants.DEFAULT_CITY);
    }

    private void setUpAutoCompleteFragment() {
        autocompleteFragment = (SupportPlaceAutocompleteFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mCityName = place.getName().toString();
//                mCountryCode = place.getLocale().getISO3Country();
                loadWeather(mCityName + "," + mCountryCode);
                Log.i("AutoCompleteFragment", "onPlaceSelected: " + mCityName + " code: " + mCountryCode);
            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    private String getCountryCodeFromeLocal(Locale locale) {

        return null;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

//    private void setUpToolbar() {
//        setSupportActionBar(toolbar);
//        placeAdapter = new AdapterArrayPlace(this, android.R.layout.simple_list_item_1,
//                null, null);
//
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .enableAutoManage(this, 0, this)
//                .addConnectionCallbacks(this)
//                .build();
//        placeAdapter.setGoogleApiClient(googleApiClient);
//        autoCompleteSearch.setAdapter(placeAdapter);
//    }

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


//
//    @Override
//    public void onConnected(Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }
}
