package com.exercise.caraugmentedreality.Contract;

public interface InternalGuideContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToHomeScreen();
        void moveOnBack();
        void moveToNotifications();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }

}
