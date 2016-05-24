package com.joaoretamero.sunshine.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.joaoretamero.sunshine.R;
import com.joaoretamero.sunshine.settings.SettingsActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new DetailFragment())
                    .commit();
        }

        Log.d(TAG, "onCreate: x");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: x");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: x");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: x");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: x");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: x");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(DetailActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
