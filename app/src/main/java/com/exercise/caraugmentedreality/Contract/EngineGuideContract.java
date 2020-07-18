package com.exercise.caraugmentedreality.Contract;

public interface EngineGuideContract {
    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToHomeScreen();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
