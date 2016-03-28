package myapp.tae.ac.uk.myweatherapp.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.concurrent.TimeUnit;

import myapp.tae.ac.uk.myweatherapp.R;
import myapp.tae.ac.uk.myweatherapp.constants.Constants;

/**
 * Created by Karma on 24/03/16.
 */
public class PlaceAutocompleteAdapter extends CursorAdapter {
    private Context context;
//    private LatLngBounds searchBounds;
//    private AutocompleteFilter searchFilter;
//    private GoogleApiClient googleApiClient;
//    private Cursor mCursor;

//    public PlaceAutocompleteAdapter(Context context, Cursor c, boolean autoRequery,
//                                    LatLngBounds searchBounds, AutocompleteFilter filter) {
//        super(context, c, autoRequery);
//        this.context = context;
//        this.searchBounds = searchBounds;
//        this.searchFilter = filter;
//    }

    public PlaceAutocompleteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_row_layout, parent, false);
        return view;
    }
//
//    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
//        if (googleApiClient == null || !googleApiClient.isConnected())
//            return;
//        this.googleApiClient = googleApiClient;
//    }
//
//    public Cursor getPlacePrediction(String query) {
//        if (googleApiClient != null && googleApiClient.isConnected()) {
//            PendingResult<AutocompletePredictionBuffer> pResults = Places.GeoDataApi
//                    .getAutocompletePredictions(googleApiClient, query,
//                            searchBounds, searchFilter);
//            AutocompletePredictionBuffer predictions = pResults.await(60, TimeUnit.SECONDS);
//            final Status status = predictions.getStatus();
//            if (!status.isSuccess()) {
//                predictions.release();
//                return null;
//            }
//            String columns[] = {BaseColumns._ID, Constants.GOOGLE_PLACE_CURSOR_CITY_ID, Constants.GOOGLE_PLACE_CURSOR_CITY,
//                    Constants.GOOGLE_PLACE_CURSOR_COUNTRY};
//            MatrixCursor cursor = new MatrixCursor(columns);
//            int i = 0;
//            for (AutocompletePrediction prediction : predictions) {
//                Object[] row = new Object[]{i, prediction.getPlaceId(),
//                        prediction.getPrimaryText(null).toString(), prediction.getSecondaryText(null).toString()};
//                cursor.addRow(row);
//                i++;
//            }
//            return cursor;
//        }
//        return null;
//    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvCityName = (TextView) view.findViewById(R.id.tvSearchResultCityName);
        TextView tvCountryName = (TextView) view.findViewById(R.id.tvSearchResultCountry);
        tvCityName.setText(cursor.getString(cursor.getColumnIndex(Constants.GOOGLE_PLACE_CURSOR_CITY)));
        tvCountryName.setText(cursor.getString(cursor.getColumnIndex(Constants.GOOGLE_PLACE_CURSOR_COUNTRY)));
    }


//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                String query = constraint != null ? constraint.toString() : "";
//                FilterResults filterResults = new FilterResults();
//                Cursor cursor = getPlacePrediction(query);
//                if (cursor != null)
//                    mCursor = cursor;
//                if (constraint != null) {
//                    filterResults.values = mCursor;
//                    filterResults.count = mCursor.getCount();
//                    if (cursor != null)
//                        changeCursor(mCursor);
//                }
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                if (results != null && results.count > 0) {
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
//            }
//        };
//        return filter;
//    }
}
