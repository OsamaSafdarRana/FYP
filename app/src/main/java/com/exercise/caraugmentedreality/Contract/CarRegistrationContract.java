package com.exercise.caraugmentedreality.Contract;

public interface CarRegistrationContract {
    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void saveToDB();
        void getRegNo();
        void moveToHomeScreen();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
