package com.exercise.caraugmentedreality.View.Activity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.ReminderFragment;

public class ReminderActivity extends BaseActivity {

    ReminderFragment mReminderFragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            mReminderFragment = new ReminderFragment();
            addFragment(R.id.fragmentContainer, mReminderFragment);
        }
    }
}
