package myapp.tae.ac.uk.myweatherapp.ui;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.myweatherapp.R;

/**
 * Created by Karma on 24/03/16.
 */
public class SearchableActivity extends ListActivity {
    @Bind(R.id.lvSearchableList)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_list_layout);
        ButterKnife.bind(this);
        handleQueryIntent();
    }

    private void handleQueryIntent() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
