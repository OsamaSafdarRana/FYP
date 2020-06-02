package com.exercise.caraugmentedreality.Contract;

import android.view.View;

public interface HomeContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToTroubleshooting();
        void moveToGuide();
        void moveToJournal();
        void moveToNotifications();
        void moveToHelp1();
        void moveToHelp2();
        void moveToHelp3();
        void fetchRegNo();
        void moveToSignIn();
        void showMenu();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}