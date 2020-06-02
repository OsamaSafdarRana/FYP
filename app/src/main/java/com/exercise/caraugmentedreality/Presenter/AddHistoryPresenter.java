package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.AddHistoryContract;

import java.lang.ref.WeakReference;

public class AddHistoryPresenter implements AddHistoryContract.Presenter {
    public WeakReference<AddHistoryContract.View> mView;

    public AddHistoryPresenter(AddHistoryContract.View view){
        mView = new WeakReference<>(view);
    }
}
