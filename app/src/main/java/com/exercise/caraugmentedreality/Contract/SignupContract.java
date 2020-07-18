package com.exercise.caraugmentedreality.Contract;

public interface SignupContract {

    interface View {
        void showProgress();
        void showMessage(String message);
        void moveToCarRegistration();
        void verifyEmail();
        void moveToSignin();
        void registerUser();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
