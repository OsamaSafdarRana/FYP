package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.HealthScoreContract;

import java.lang.ref.WeakReference;

public class HealthScorePresenter implements HealthScoreContract.Presenter {
    public WeakReference<HealthScoreContract.View> mView;

    public HealthScorePresenter(HealthScoreContract.View view) {
        mView = new WeakReference<>(view);
    }
}
