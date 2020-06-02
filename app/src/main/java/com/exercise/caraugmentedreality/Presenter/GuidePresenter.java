package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.GuideContract;

import java.lang.ref.WeakReference;

public class GuidePresenter implements GuideContract.Presenter {
    public WeakReference<GuideContract.View> mView;

    public GuidePresenter(GuideContract.View view){mView = new WeakReference<>(view);}
}
