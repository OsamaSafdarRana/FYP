package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.MenuContract;

import java.lang.ref.WeakReference;

public class MenuPresenter implements MenuContract.Presenter {
    public WeakReference<MenuContract.View> mView;

    public MenuPresenter(MenuContract.View view) {
        mView = new WeakReference<>(view);
    }
}
