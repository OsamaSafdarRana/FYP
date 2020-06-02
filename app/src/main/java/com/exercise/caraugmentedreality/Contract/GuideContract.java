package com.exercise.caraugmentedreality.Contract;

public interface GuideContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToInternalGuide();
        void moveToExternalGuide();
        void moveToHomeScreen();
        void moveToNotifications();
        void moveToHelp1();
        void moveToHelp2();
    }

    interface Presenter {

    }
}
