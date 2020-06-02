package com.exercise.caraugmentedreality.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.CarRegistrationFragment;

public class CarRegistrationActivity extends BaseActivity {

    CarRegistrationFragment mFragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            mFragment = new CarRegistrationFragment();
            addFragment(R.id.fragmentContainer,mFragment);
        }
    }
}
