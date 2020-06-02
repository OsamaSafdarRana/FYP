package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.CarRegistrationContract;

import java.lang.ref.WeakReference;

public class CarRegistrationPresenter implements CarRegistrationContract.Presenter {
    public WeakReference<CarRegistrationContract.View> mView;

    public CarRegistrationPresenter(CarRegistrationContract.View view) {
        mView = new WeakReference<>(view);
    }
}
