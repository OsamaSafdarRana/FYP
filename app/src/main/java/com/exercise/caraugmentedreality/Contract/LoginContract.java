package com.exercise.caraugmentedreality.Contract;

public interface LoginContract {

    interface View {
        void showProgress();
        void showMessage(String message);
        void signIn();
        void moveToSignup();
        void moveToSelectCar();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
