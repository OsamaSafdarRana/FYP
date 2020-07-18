package com.exercise.caraugmentedreality.Contract;

public interface AddReminderContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void saveData();
        void moveToReminderScreen();
        void moveToNotifications();

    }

    interface Presenter {

    }
}
