package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.TroubleshootContract;

import java.lang.ref.WeakReference;

public class TroubleshootPresenter implements TroubleshootContract.Presenter {
    private WeakReference<TroubleshootContract.View> mView;

    public TroubleshootPresenter(TroubleshootContract.View view){
        mView = new WeakReference<>(view);
    }
}
