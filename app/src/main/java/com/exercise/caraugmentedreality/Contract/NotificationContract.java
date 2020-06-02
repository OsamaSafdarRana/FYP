package com.exercise.caraugmentedreality.Contract;

public interface NotificationContract {
    interface View{
        void showProgress();
        void showMessage(String message);
        void openNotification();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
