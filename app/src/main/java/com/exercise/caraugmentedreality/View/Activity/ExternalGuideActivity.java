package com.exercise.caraugmentedreality.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.ExternalGuideFragment;

public class ExternalGuideActivity extends BaseActivity {

    ExternalGuideFragment mFragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            mFragment = new ExternalGuideFragment();
            addFragment(R.id.fragmentContainer,mFragment);
        }
    }
}
