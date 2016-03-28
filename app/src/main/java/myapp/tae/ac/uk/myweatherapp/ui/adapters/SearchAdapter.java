package myapp.tae.ac.uk.myweatherapp.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import myapp.tae.ac.uk.myweatherapp.model.google.PlaceAutoCompleteData;

/**
 * Created by Karma on 24/03/16.
 */
public class SearchAdapter extends ArrayAdapter<PlaceAutoCompleteData> implements Filterable {
    private static final String TAG = "AutoCompleteAdapter";
    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private LatLngBounds mSearchBounds;
    private AutocompleteFilter mPlaceFilter;
    private ArrayList<PlaceAutoCompleteData> mResultList;

    public SearchAdapter(Context context, int resource, LatLngBounds searchBounds,
                         AutocompleteFilter placeFilter) {
        super(context, resource);
        this.context = context;
        this.mPlaceFilter = placeFilter;
        this.mSearchBounds = searchBounds;
    }

    public void setmGoogleApiClient(GoogleApiClient googleApiClient) {
        if (googleApiClient == null && !googleApiClient.isConnected()) {
            Log.i(TAG, "client :null or not connected");
            return;
        } else {
            mGoogleApiClient = googleApiClient;
        }
    }

    public ArrayList<PlaceAutoCompleteData> getPredictions(CharSequence constraints) {
        String queryText = constraints != null ? constraints.toString() : "";
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            PendingResult<AutocompletePredictionBuffer> pResults = Places.GeoDataApi
                    .getAutocompletePredictions(mGoogleApiClient, queryText,
                            mSearchBounds, mPlaceFilter);
            AutocompletePredictionBuffer predictions = pResults.await(60, TimeUnit.SECONDS);
            final Status status = predictions.getStatus();
            if (!status.isSuccess()) {
                predictions.release();
                Log.i(TAG, "getPredictions: Status: " + status.getStatusMessage());
                return null;
            }
            ArrayList<PlaceAutoCompleteData> resultList = new ArrayList<>();
            Iterator<AutocompletePrediction> resultIterator = predictions.iterator();
            while (resultIterator.hasNext()) {
                AutocompletePrediction prediction = resultIterator.next();
                PlaceAutoCompleteData autocomplete = new PlaceAutoCompleteData();
                autocomplete.setId(prediction.getPlaceId());
                autocomplete.setCityCountry(prediction.getFullText(null).toString());
                resultList.add(autocomplete);
            }
            predictions.release();
            Log.i(TAG, "getPredictions: is Success");
            return resultList;
        }
        Log.i(TAG, "getPredictions: Client is not Connected");
        return null;
    }

    @Override
    public PlaceAutoCompleteData getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public Filter getFilter() {
        Filter mFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults mFilterResults = new FilterResults();
                mResultList = getPredictions(constraint);
                if (constraint != null) {
                    if (mFilterResults != null) {
                        mFilterResults.values = mResultList;
                        mFilterResults.count = mResultList.size();
                        Log.i(TAG, "performFiltering: Result size: " + mResultList.size());
                    }
                }
                Log.i(TAG, "performFiltering: Success");
                return mFilterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    Log.i(TAG, "publishResults: null or is empty");
                    notifyDataSetInvalidated();
                }
            }

        };

        return mFilter;
    }
}
