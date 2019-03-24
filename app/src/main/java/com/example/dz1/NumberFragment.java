package com.example.dz1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class NumberFragment extends Fragment {

    Integer selectedValue = 0;
    TextView mItemText;

    public String DATA_KEY = "SELECTED_VALUE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_number, container, false);
    }

    @Override
    public void onStart() {
        mItemText = Objects.requireNonNull(getView()).findViewById(R.id.item);
        mItemText.setText(String.valueOf(selectedValue));
        int color = (selectedValue % 2 == 0) ? Color.RED : Color.BLUE;
        mItemText.setBackgroundColor(color);
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            selectedValue = savedInstanceState.getInt(DATA_KEY, 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DATA_KEY, selectedValue);
    }

}