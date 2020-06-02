package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.ShowHistoryContract;

import java.lang.ref.WeakReference;

public class ShowHistoryPresenter implements ShowHistoryContract.Presenter {
    public WeakReference<ShowHistoryContract.View> mView;

    public ShowHistoryPresenter(ShowHistoryContract.View view){mView = new WeakReference<>(view);}
}
