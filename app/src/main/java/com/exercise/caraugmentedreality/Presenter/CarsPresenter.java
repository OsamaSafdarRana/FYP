package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.CarsContract;

import java.lang.ref.WeakReference;

public class CarsPresenter implements CarsContract.Presenter {
    public WeakReference<CarsContract.View> mView;

    public CarsPresenter(CarsContract.View view){
        mView = new WeakReference<>(view);
    }
}
