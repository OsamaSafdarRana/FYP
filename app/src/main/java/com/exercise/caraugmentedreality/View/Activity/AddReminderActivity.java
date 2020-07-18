package com.exercise.caraugmentedreality.View.Activity;

import android.os.Bundle;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.AddReminderFragment;

public class AddReminderActivity extends BaseActivity {

    AddReminderFragment mAddReminderFragment;

    @Override
    protected void onPreStart() {
        super.onPreStart();
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState==null){
            mAddReminderFragment = new AddReminderFragment();
            addFragment(R.id.fragmentContainer, mAddReminderFragment);
        }
    }
}
