package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.InternalGuideContract;

import java.lang.ref.WeakReference;

public class InternalGuidePresenter implements InternalGuideContract.Presenter {
    public WeakReference<InternalGuideContract.View> mView;

    public InternalGuidePresenter(InternalGuideContract.View view){mView = new WeakReference<>(view);}
}
