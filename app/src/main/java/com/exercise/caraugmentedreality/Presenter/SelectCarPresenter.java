package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.SelectCarContract;

import java.lang.ref.WeakReference;

public class SelectCarPresenter implements SelectCarContract.Presenter {
    public WeakReference<SelectCarContract.View> mView;

    public SelectCarPresenter(SelectCarContract.View view){
        mView = new WeakReference<>(view);
    }
}
