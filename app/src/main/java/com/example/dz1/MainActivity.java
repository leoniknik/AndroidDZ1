package com.example.dz1;

import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements NumberListDelegate {

    int numberOfItems = 5;
    int numbersOfColumns = 3;

    public String DATA_KEY = "NUMBER_OF_ITEMS";

    private static final String LIST_FRAGMENT_TAG = "LIST_FRAGMENT_TAG";
    private static final String NUMBER_FRAGMENT_TAG = "NUMBER_FRAGMENT_TAG";

    NumberListFragment numberListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        numbersOfColumns = isPortraitOrientation() ? 3 : 4;

        if (savedInstanceState != null) {
            numberOfItems = savedInstanceState.getInt(DATA_KEY, 100);
        }

        numberListFragment = new NumberListFragment();
        numberListFragment.fillData(numberOfItems);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_number_list_container, numberListFragment, LIST_FRAGMENT_TAG).
                    commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void numberPressed(int number) {
        Integer selectedValue = numberListFragment.numbers.get(number);
        NumberFragment numberFragment = new NumberFragment();
        numberFragment.selectedValue = selectedValue;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_number_list_container, numberFragment, NUMBER_FRAGMENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getNumbersOfColumns() {
        return numbersOfColumns;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private Boolean isPortraitOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
