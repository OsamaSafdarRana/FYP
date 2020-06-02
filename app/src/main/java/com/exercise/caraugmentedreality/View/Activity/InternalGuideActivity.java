package com.exercise.caraugmentedreality.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.BaseFragment;
import com.exercise.caraugmentedreality.View.Fragment.HomeFragment;
import com.exercise.caraugmentedreality.View.Fragment.InternalGuideFragment;

public class InternalGuideActivity extends BaseActivity {
    InternalGuideFragment mInternalGuideFragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            mInternalGuideFragment = new InternalGuideFragment();
            addFragment(R.id.fragmentContainer,mInternalGuideFragment);
        }
    }
}
