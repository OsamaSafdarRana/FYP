package com.exercise.caraugmentedreality.View.Activity;

import android.os.Bundle;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.HomeFragment;

public class HomeActivity extends BaseActivity {

  HomeFragment mHomeFragment;

  @Override
  protected void onPreStart() {
    super.onPreStart();
    setContentView(R.layout.activity_base);
  }

  @Override
  protected void onPostStart(Bundle savedInstanceState){
    super.onPostStart(savedInstanceState);

    if(savedInstanceState==null){
      mHomeFragment = new HomeFragment();
      addFragment(R.id.fragmentContainer, mHomeFragment);
    }
  }
}
