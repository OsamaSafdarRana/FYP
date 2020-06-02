package com.exercise.caraugmentedreality.Contract;

public interface JournalContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToAddHistory();
        void moveToShowHistory();
        void moveToHealth();
        void moveToHomeScreen();
        void moveToNotifications();
        void moveToHelp1();
        void moveToHelp2();
        void moveToHelp3();
    }
    interface Presenter {

    }
}
