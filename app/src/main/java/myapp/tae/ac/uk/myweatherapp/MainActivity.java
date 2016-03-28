package myapp.tae.ac.uk.myweatherapp;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myweatherapp.api.IWeatherAPI;
import myapp.tae.ac.uk.myweatherapp.constants.Constants;
import myapp.tae.ac.uk.myweatherapp.model.weather.WeatherInfo;
import myapp.tae.ac.uk.myweatherapp.model.weather.Wind;
import myapp.tae.ac.uk.myweatherapp.presenter.GooglePlaceService;
import myapp.tae.ac.uk.myweatherapp.presenter.IWeatherView;
import myapp.tae.ac.uk.myweatherapp.presenter.WeatherPresenter;
import myapp.tae.ac.uk.myweatherapp.ui.adapters.AdapterArrayPlace;
import myapp.tae.ac.uk.myweatherapp.ui.adapters.AdapterViewPager;
import myapp.tae.ac.uk.myweatherapp.ui.adapters.PlaceAutocompleteAdapter;
import myapp.tae.ac.uk.myweatherapp.ui.fragments.FragmentTodayWeather;
import myapp.tae.ac.uk.myweatherapp.ui.fragments.FragmentWindIfo;
import myapp.tae.ac.uk.myweatherapp.util.JSONUtil;

public class MainActivity extends AppCompatActivity implements IWeatherView, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    @Inject
    IWeatherAPI mIWeatherAPI;
    @Bind(R.id.vpPager)
    ViewPager viewPager;
    @Bind(R.id.weatherToolbar)
    Toolbar toolbar;
    @Bind(R.id.action_searchAutocomplete)
    SearchView searchAutoComplete;
    //    AutoCompleteTextView autoCompleteSearch;
//        SupportPlaceAutocompleteFragment autocompleteFragment;
    private final static String TAG = "AutoCompleteFragment";

    private AdapterViewPager adapter;
    private ProgressDialog progressDialog;
    private WeatherPresenter weatherPresenter;
    private WeatherInfo weatherInfo;
    private LatLngBounds latLngBounds;
    ;
    private AutocompleteFilter searchFilter;
    //    private AdapterArrayPlace placeAdapter;
    private PlaceAutocompleteAdapter searchSuggestionAdapter;
    private GoogleApiClient googleApiClient;
    private ResultCallback<PlaceBuffer> mPlaceDetailCallback;
    private GooglePlaceService placeService;
    private JSONUtil mJsonUtil;
    private String placeSearchCountry = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpInjections();
        setUpToolbar();
        setUpProgressDialog();
        setGoogleClient();
        setUpResultCallback();
        mJsonUtil = new JSONUtil(this, "slim-2.json");
        weatherPresenter = new WeatherPresenter(this, mIWeatherAPI);
        adapter = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        prepareSearchView();
        loadWeather(Constants.DEFAULT_CITY);
    }

    private void setUpResultCallback() {
        mPlaceDetailCallback = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                Status status = places.getStatus();
                if (!status.isSuccess())
                    return;
                final Place place = places.get(0);
                String weatherQuery = place.getName().toString();
                if (placeSearchCountry.contains(",")) {
                    int indexofLastComma = placeSearchCountry.lastIndexOf(',');
                    placeSearchCountry = placeSearchCountry.substring(indexofLastComma + 2);
                }
                String countryCode = mJsonUtil.getCountryAlpha2Code(placeSearchCountry);
                if (!countryCode.isEmpty())
                    weatherQuery += "," + countryCode;

                loadWeather(weatherQuery);
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                Log.i(TAG, "onResult: " + weatherQuery);
            }
        };
    }

    private void prepareSearchView() {
        searchAutoComplete.setQueryHint(getText(R.string.search_hint));
        setupAutoCompleteAdapter();
        searchAutoComplete.setSuggestionsAdapter(searchSuggestionAdapter);
        searchAutoComplete.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                new PlaceCursorLoader().execute(newText);
                return false;
            }
        });

        searchAutoComplete.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = searchAutoComplete.getSuggestionsAdapter().getCursor();
                cursor.move(position);
                String placeId = cursor.getString(cursor.getColumnIndex(Constants.GOOGLE_PLACE_CURSOR_CITY_ID));
                String cityName = cursor.getString(cursor.getColumnIndex(Constants.GOOGLE_PLACE_CURSOR_CITY));
                placeSearchCountry = cursor.getString(cursor.getColumnIndex(Constants.GOOGLE_PLACE_CURSOR_COUNTRY));
                searchAutoComplete.setQuery(cityName, false);
                PendingResult<PlaceBuffer> placeResults = Places.GeoDataApi.getPlaceById(googleApiClient, placeId);
                placeResults.setResultCallback(mPlaceDetailCallback);
                return true;
            }
        });
    }

    private void setupAutoCompleteAdapter() {
        latLngBounds = new LatLngBounds(new LatLng(0, 0), new LatLng(0, 0));
        searchFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        placeService = new GooglePlaceService(latLngBounds, searchFilter);
        weatherPresenter.setGooglePlaceService(placeService);
        searchSuggestionAdapter = new PlaceAutocompleteAdapter(this, null, false);

    }

    private void setGoogleClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .build();
    }

//    private void setUpAutoCompleteFragment() {
//        autocompleteFragment = (SupportPlaceAutocompleteFragment) getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
//                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
//                .build();
//        autocompleteFragment.setFilter(typeFilter);
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                mCityName = place.getName().toString();
//                loadWeather(mCityName + "," + mCountryCode);
//                Log.i("AutoCompleteFragment", "onPlaceSelected: " + mCityName + " code: " + mCountryCode);
//                Log.i(TAG, "onPlaceSelected: " + place.getId());
//                Log.i(TAG, "onPlaceSelected: " + place.getLatLng() + " ");
//            }
//
//            @Override
//            public void onError(Status status) {
//
//            }
//        });
//    }

    private String getCountryCodeFromeLocal(Locale locale) {

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu) | true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

    @Override
    public void onConnected(Bundle bundle) {
        weatherPresenter.setGoogleApiClientForPlaceService(googleApiClient);
//        searchSuggestionAdapter.setGoogleApiClient(googleApiClient);
        Log.i(TAG, "onConnected: Success");
    }

    @Override
    public void onConnectionSuspended(int i) {

//        searchSuggestionAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed: errors " + connectionResult.getErrorMessage());
    }

    public class PlaceCursorLoader extends AsyncTask<String, Void, Cursor> {

        @Override
        protected Cursor doInBackground(String... params) {
            return weatherPresenter.getPlaceAutocompleteCursor(params[0]);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            searchAutoComplete.getSuggestionsAdapter().changeCursor(cursor);
        }
    }

}
