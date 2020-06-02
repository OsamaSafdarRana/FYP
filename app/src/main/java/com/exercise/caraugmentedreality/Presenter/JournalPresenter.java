package com.exercise.caraugmentedreality.Presenter;

import com.exercise.caraugmentedreality.Contract.JournalContract;

import java.lang.ref.WeakReference;

public class JournalPresenter implements JournalContract.Presenter {
    public WeakReference<JournalContract.View> mView;

    public JournalPresenter(JournalContract.View view) {
        mView = new WeakReference<>(view);
    }
}
