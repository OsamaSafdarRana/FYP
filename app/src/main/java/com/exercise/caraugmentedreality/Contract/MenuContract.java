package com.exercise.caraugmentedreality.Contract;

public interface MenuContract {
    interface View{
        void showProgress();
        void showMessage(String message);
        void moveToAddCar();
        void moveToViewCar();
        void moveToSelectCar();
        void moveToLogin();
        void moveToHomeScreen();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
