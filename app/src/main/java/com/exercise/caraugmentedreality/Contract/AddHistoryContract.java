package com.exercise.caraugmentedreality.Contract;

public interface AddHistoryContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToNotifications();
        void moveToHomeScreen();
        void moveToJournal();
        void storeData();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
