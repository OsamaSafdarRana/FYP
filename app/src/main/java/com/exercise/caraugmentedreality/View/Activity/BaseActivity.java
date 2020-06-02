package com.exercise.caraugmentedreality.View.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        onPreStart();


        // calling ButterKnife right after setting content view in Pre start
        // binding ButterKnife for views
        ButterKnife.bind(this);


        onInitListeners();
        onPostStart(savedInstanceState);
    }



    /*
     * for setting content view
     */
    protected void onPreStart() {

    }

    /*
     * registration of listeners
     */
    protected void onInitListeners() {

    }

    /*
     * last call of creating activity
     * mostly for adding fragments
     */
    protected void onPostStart(Bundle savedInstanceState) {

    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *  @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    /*
     * @param fragment The fragment to be removed.
     */
    protected void removeFragment(Fragment fragment) {
        if (fragment != null) {
            final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            try {
                fragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                fragmentTransaction.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}


