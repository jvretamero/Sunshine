package com.joaoretamero.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();
    private String forecastStr;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            forecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView textView = (TextView) rootView.findViewById(R.id.detail_text);
            textView.setText(forecastStr);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_fragment, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        if (menuItem != null) {
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if (shareActionProvider != null) {
                shareActionProvider.setShareIntent(createShareIntent());
            } else {
                Log.d(TAG, "onCreateOptionsMenu: ShareActionProvider nulo");
            }
        }
    }

    private Intent createShareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, forecastStr + " #SunshineApp");

        return intent;
    }
}
