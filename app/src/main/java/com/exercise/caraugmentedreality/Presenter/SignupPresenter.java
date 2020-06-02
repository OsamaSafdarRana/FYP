package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.SignupContract;

import java.lang.ref.WeakReference;

public class SignupPresenter implements SignupContract.Presenter {

    public WeakReference<SignupContract.View> mView;

    public SignupPresenter(SignupContract.View view) {
        mView = new WeakReference<>(view);
    }

}
