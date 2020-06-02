package com.exercise.caraugmentedreality.View.Activity;

import android.os.Bundle;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Fragment.TroubleshootFragment;

public class TroubleshootActivity extends BaseActivity {

  TroubleshootFragment mTroubleshootFragment;

  @Override
  protected void onPreStart() {
    super.onPreStart();
    setContentView(R.layout.activity_base);
  }

  @Override
  protected void onPostStart(Bundle savedInstanceState) {
    super.onPostStart(savedInstanceState);

    if(savedInstanceState== null){
      mTroubleshootFragment = new TroubleshootFragment();
      addFragment(R.id.fragmentContainer,mTroubleshootFragment);
    }
  }
}
