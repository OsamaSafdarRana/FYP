package com.exercise.caraugmentedreality.Contract;

public interface ShowHistoryContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToNotifications();
        void moveToHomeScreen();
        void moveOnBack();
        void showData();
    }

    interface Presenter {
        // validation methods
        // api calling method
    }
}