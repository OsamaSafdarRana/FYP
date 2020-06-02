package com.exercise.caraugmentedreality.View.Activity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.ShowHistoryFragment;

public class ShowHistoryActivity extends BaseActivity {

    ShowHistoryFragment fragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            fragment = new ShowHistoryFragment();
            addFragment(R.id.fragmentContainer,fragment);
        }
    }
}
