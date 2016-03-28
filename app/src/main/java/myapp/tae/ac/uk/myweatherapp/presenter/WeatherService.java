package myapp.tae.ac.uk.myweatherapp.presenter;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import myapp.tae.ac.uk.myweatherapp.model.google.PlaceAutoCompleteData;

/**
 * Created by Karma on 13/03/16.
 */
public class WeatherService {
    private GoogleApiClient mGoogleApiClient;
    private LatLngBounds mBounds;
    private AutocompleteFilter mFilter;

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        if (googleApiClient == null && !googleApiClient.isConnected()) {
            return;
        } else {
            this.mGoogleApiClient = googleApiClient;
        }
    }

    public ArrayList<PlaceAutoCompleteData> getPredictions(CharSequence constraint) {
        if (constraint != null && mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            PendingResult<AutocompletePredictionBuffer> results = Places
                    .GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(), mBounds, mFilter);
            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                autocompletePredictions.release();
                return null;
            }
            ArrayList<PlaceAutoCompleteData> resultList = new ArrayList<>(autocompletePredictions.getCount());
            for (AutocompletePrediction prediction : autocompletePredictions) {
                PlaceAutoCompleteData placeAutoComplete = new PlaceAutoCompleteData();
                placeAutoComplete.setId(prediction.getPlaceId());
                placeAutoComplete.setCityCountry(prediction.getSecondaryText(null).toString());
                resultList.add(placeAutoComplete);
            }
            return resultList;
        }
        return null;
    }

    public void setBounds(LatLngBounds bounds) {
        this.mBounds = bounds;
    }

    public void setFilter(AutocompleteFilter filter) {
        this.mFilter = filter;
    }
}
