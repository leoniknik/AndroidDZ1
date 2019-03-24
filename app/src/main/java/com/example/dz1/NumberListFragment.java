package com.example.dz1;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

interface NumberListDelegate {
    void numberPressed(int number);
    int getNumbersOfColumns();
}

public class NumberListFragment extends Fragment {

    ArrayList<Integer> numbers = new ArrayList();
    public String DATA_KEY = "NUMBER_OF_ITEMS";

    RecyclerView mList;
    NumberListDelegate delegate;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NumberListDelegate) {
            delegate = (NumberListDelegate) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_number_list, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mList = view.findViewById(R.id.number_list);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(DATA_KEY, numbers.size());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        final RecyclerView recyclerView = getView().findViewById(R.id.number_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), delegate.getNumbersOfColumns()));
        recyclerView.setAdapter(new NumberListAdapter(numbers));

        Button addButton = getView().findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNumber();
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void fillData(int numberOfItems) {
        numbers.clear();
        for (int i = 1; i <= numberOfItems; i++) {
            numbers.add(i);
        }
    }

    public void addNumber() {
        Integer last = (Integer) numbers.get(numbers.size() - 1);
        last++;
        numbers.add(last);
    }

    class NumberListViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public NumberListViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.number_text);
        }
    }

    class NumberListAdapter extends RecyclerView.Adapter<NumberListViewHolder> {

        private List<Integer> mData;

        public NumberListAdapter(List<Integer> data) {
            mData = data;
        }

        @NonNull
        @Override
        public NumberListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.list_element, viewGroup, false);
            return new NumberListViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull NumberListViewHolder numberListViewHolder, final int i) {
            int number = mData.get(i);
            numberListViewHolder.mTextView.setText(String.valueOf(number));
            int color = (number % 2 == 0) ? Color.RED : Color.BLUE;
            numberListViewHolder.mTextView.setBackgroundColor(color);
            numberListViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delegate.numberPressed(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
