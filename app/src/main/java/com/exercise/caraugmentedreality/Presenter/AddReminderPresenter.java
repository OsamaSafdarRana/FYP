package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.AddReminderContract;

import java.lang.ref.WeakReference;

public class AddReminderPresenter implements AddReminderContract.Presenter {
    public WeakReference<AddReminderContract.View> mView;

    public AddReminderPresenter(AddReminderContract.View view){mView = new WeakReference<>(view);}
}
