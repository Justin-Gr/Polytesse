package com.fisea.polytesse.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fisea.polytesse.R;

import java.util.List;

public class SlangWordsCountListAdapter extends ArrayAdapter<CoupleOriginSlangWordsCount> {

    int resource;

    public SlangWordsCountListAdapter(Context context, int resource, List<CoupleOriginSlangWordsCount> couples) {
        super(context, resource, couples);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String origin = getItem(position).getOrigin();
        int count = getItem(position).getSlangWordsCount();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(resource, parent, false);

        TextView tvOrigin = convertView.findViewById(R.id.origin);
        TextView tvSlangCount = convertView.findViewById(R.id.slang_count);

        tvOrigin.setText(origin);
        tvSlangCount.setText(String.valueOf(count));

        return convertView;
    }
}
