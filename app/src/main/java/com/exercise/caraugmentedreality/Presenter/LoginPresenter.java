package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.LoginContract;

import java.lang.ref.WeakReference;

public class LoginPresenter implements LoginContract.Presenter {
    public WeakReference<LoginContract.View> mView;

    public LoginPresenter(LoginContract.View view) {
        mView = new WeakReference<>(view);
    }

}
