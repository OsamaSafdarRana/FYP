package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.ReminderContract;

import java.lang.ref.WeakReference;

public class ReminderPresenter implements ReminderContract.Presenter {
    public WeakReference<ReminderContract.View> mView;

    public ReminderPresenter(ReminderContract.View view) {
        mView = new WeakReference<>(view);
    }
}
