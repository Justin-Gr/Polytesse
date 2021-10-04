package com.fisea.polytesse.services;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fisea.polytesse.R;
import com.fisea.polytesse.helpers.DatabaseHelper;

import java.util.List;
import java.util.Locale;

public class SlangWordsCounterService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String origin = intent.getStringExtra("origin");
        String SMSMessage = intent.getStringExtra("SMSMessage");

        List<String> slangWords = asList(getResources().getStringArray(R.array.slang_words));

        List<String> words = asList(
                SMSMessage.toLowerCase(Locale.ROOT)
                .replaceAll("[!?,.]", "")
                .split("\\s+")
        );

        long count = words.stream().filter(slangWords::contains).count();

        if (count > 0) {
            Toast.makeText(this,
                    format(getResources().getString(R.string.slang_words_reception_toast), origin, count),
                    Toast.LENGTH_LONG
            ).show();

            // Persistence
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            databaseHelper.saveSlangWordsCountForOrigin(origin, (int) count);

            // Update the list
            Intent refreshIntent = new Intent("com.fisea.polytesse.refreshList");
            sendBroadcast(refreshIntent);
        }

        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
