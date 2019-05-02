package com.teenspirit88.musicalbums;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;

public class SearchableActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    public void handleIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

        }
    }
}
