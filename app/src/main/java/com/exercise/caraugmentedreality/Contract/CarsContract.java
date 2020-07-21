package com.exercise.caraugmentedreality.Contract;

public interface CarsContract {
    interface View {
        void showProgress();
        void showMessage(String message);
        void moveToHomeScreen();
        void saveToDB();
        void moveToAddCarScreen();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
