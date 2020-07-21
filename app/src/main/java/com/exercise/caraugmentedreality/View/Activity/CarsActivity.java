package com.exercise.caraugmentedreality.View.Activity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.CarsFragment;

public class CarsActivity extends BaseActivity {

    CarsFragment mFragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            mFragment = new CarsFragment();
            addFragment(R.id.fragmentContainer,mFragment);
        }
    }
}
