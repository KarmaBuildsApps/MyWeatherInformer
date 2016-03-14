package myapp.tae.ac.uk.myweatherapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

import myapp.tae.ac.uk.myweatherapp.R;
import myapp.tae.ac.uk.myweatherapp.model.google.PlaceAutoComplete;
import myapp.tae.ac.uk.myweatherapp.presenter.WeatherService;

/**
 * Created by Karma on 13/03/16.
 */
public class AdapterArrayPlace extends ArrayAdapter<PlaceAutoComplete> implements Filterable {
    private Context context;
    private LatLngBounds mLimitSearchLatLngBounds;
    private AutocompleteFilter mSearchAutoCompleteFilter;
    private ArrayList<PlaceAutoComplete> mSearchResults;
    private WeatherService mService;

    public AdapterArrayPlace(Context context, int resource, LatLngBounds bounds, AutocompleteFilter filter) {
        super(context, resource);
        this.context = context;
        this.mLimitSearchLatLngBounds = bounds;
        this.mSearchAutoCompleteFilter = filter;
        this.mService = new WeatherService();
        setBounds();
        setFilter();
    }

    @Override
    public int getCount() {
        return mSearchResults.size();
    }

    @Override
    public PlaceAutoComplete getItem(int position) {
        return mSearchResults.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults mFilterResult = new FilterResults();
                if (constraint != null) {
                    mSearchResults = mService.getPredictions(constraint);
                    if (mSearchResults != null) {
                        mFilterResult.values = mSearchResults;
                        mFilterResult.count = mSearchResults.size();
                    }
                }
                return mFilterResult;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        mService.setGoogleApiClient(googleApiClient);
    }

    public void setBounds() {
        mService.setBounds(mLimitSearchLatLngBounds);
    }

    public void setFilter() {
        mService.setFilter(mSearchAutoCompleteFilter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
