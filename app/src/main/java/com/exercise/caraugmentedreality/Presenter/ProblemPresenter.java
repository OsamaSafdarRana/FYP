package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.ProblemContract;

import java.lang.ref.WeakReference;

public class ProblemPresenter implements ProblemContract.Presenter {
    public WeakReference<ProblemContract.View> mView;

    public ProblemPresenter(ProblemContract.View view){
        mView = new WeakReference<>(view);
    }
}
