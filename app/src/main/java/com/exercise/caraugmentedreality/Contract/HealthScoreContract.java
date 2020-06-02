package com.exercise.caraugmentedreality.Contract;

public interface HealthScoreContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToJournal();
        int showHealthScore();
        void moveToAddHistory();
        void moveToHomeScreen();
        void moveToNotifications();
    }

    interface Presenter {

    }
}
