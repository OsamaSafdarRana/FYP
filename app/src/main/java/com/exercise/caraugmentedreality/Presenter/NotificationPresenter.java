package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.NotificationContract;

import java.lang.ref.WeakReference;

public class NotificationPresenter implements NotificationContract.Presenter {
    public WeakReference<NotificationContract.View> mView;

    public NotificationPresenter(NotificationContract.View view) {
        mView = new WeakReference<>(view);
    }
}
