package com.exercise.caraugmentedreality.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.SelectCarFragment;

public class SelectCarActivity extends BaseActivity {

    SelectCarFragment mFragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            mFragment = new SelectCarFragment();
            addFragment(R.id.fragmentContainer,mFragment);
        }
    }
}
