package com.exercise.caraugmentedreality.Contract;

public interface ReminderContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToAddReminder();
        void moveToHomeScreen();
        void moveToNotifications();
    }
    interface Presenter {

    }
}
