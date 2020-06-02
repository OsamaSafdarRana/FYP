package com.exercise.caraugmentedreality.Presenter;

import java.lang.ref.WeakReference;
import com.exercise.caraugmentedreality.Contract.HomeContract;

public class HomePresenter implements HomeContract.Presenter{
    public WeakReference<HomeContract.View> mView;

    public HomePresenter(HomeContract.View view) {
        mView = new WeakReference<>(view);
    }
}



