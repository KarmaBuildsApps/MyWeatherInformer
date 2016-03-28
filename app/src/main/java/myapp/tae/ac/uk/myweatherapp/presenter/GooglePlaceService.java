package myapp.tae.ac.uk.myweatherapp.presenter;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.concurrent.TimeUnit;

import myapp.tae.ac.uk.myweatherapp.constants.Constants;

/**
 * Created by Karma on 28/03/16.
 */
public class GooglePlaceService {
    private static final String TAG = GooglePlaceService.class.getSimpleName();
    private LatLngBounds searchBounds;
    private AutocompleteFilter searchFilter;
    private GoogleApiClient googleApiClient;

    public GooglePlaceService(LatLngBounds searchBounds, AutocompleteFilter searchFilter) {
        this.searchBounds = searchBounds;
        this.searchFilter = searchFilter;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        if (googleApiClient == null || !googleApiClient.isConnected())
            return;
        this.googleApiClient = googleApiClient;
    }

    public Cursor getPlacePrediction(String query) {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            PendingResult<AutocompletePredictionBuffer> pResults = Places.GeoDataApi
                    .getAutocompletePredictions(googleApiClient, query,
                            searchBounds, searchFilter);
            AutocompletePredictionBuffer predictions = pResults.await(60, TimeUnit.SECONDS);
            final Status status = predictions.getStatus();
            if (!status.isSuccess()) {
                predictions.release();
                return null;
            }
            String columns[] = {BaseColumns._ID, Constants.GOOGLE_PLACE_CURSOR_CITY_ID, Constants.GOOGLE_PLACE_CURSOR_CITY,
                    Constants.GOOGLE_PLACE_CURSOR_COUNTRY};
            MatrixCursor cursor = new MatrixCursor(columns);
            int i = 0;
            for (AutocompletePrediction prediction : predictions) {
                Object[] row = new Object[]{i, prediction.getPlaceId(),
                        prediction.getPrimaryText(null).toString(), prediction.getSecondaryText(null).toString()};
                cursor.addRow(row);
//                Log.i(TAG, "getPlacePrediction: Full Text" + prediction.getFullText(null) + "Primary: " +
//                        prediction.getPrimaryText(null) + " Secondary " + prediction.getSecondaryText(null));
                i++;
            }
            return cursor;
        }
        return null;
    }

}
