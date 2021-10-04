package com.fisea.polytesse.activities;

import static java.util.Comparator.comparing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fisea.polytesse.adapters.CoupleOriginSlangWordsCount;
import com.fisea.polytesse.helpers.DatabaseHelper;
import com.fisea.polytesse.R;
import com.fisea.polytesse.adapters.SlangWordsCountListAdapter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private RefreshReceiver refreshReceiver;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        // Register to the refresh receiver
        refreshReceiver = new RefreshReceiver(new Handler());
        registerReceiver(refreshReceiver, new IntentFilter("com.fisea.polytesse.refreshList"));

        // Listen to the clear button
        findViewById(R.id.reset_btn).setOnClickListener(view -> resetList());

        // Initialize the list
        displayList();
    }

    private void displayList() {
        Map<String, Integer> data = databaseHelper.getSlangWordsCountList();

        List<CoupleOriginSlangWordsCount> couples = data.entrySet().stream()
                .map(entry -> new CoupleOriginSlangWordsCount(entry.getKey(), entry.getValue()))
                .sorted(comparing(CoupleOriginSlangWordsCount::getSlangWordsCount).reversed())
                .collect(Collectors.toList());

        SlangWordsCountListAdapter adapter = new SlangWordsCountListAdapter(this, R.layout.adapter_view_layout, couples);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void resetList() {
        databaseHelper.clearSlangWordsCountList();
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(null);

        Toast.makeText(this, getResources().getString(R.string.list_reset_toast), Toast.LENGTH_LONG).show();
    }

    private class RefreshReceiver extends BroadcastReceiver {
        private final Handler handler;

        public RefreshReceiver(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(final Context context, Intent intent) {
            handler.post(MainActivity.this::displayList);
        }
    }
}