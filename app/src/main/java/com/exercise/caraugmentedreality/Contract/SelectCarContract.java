package com.exercise.caraugmentedreality.Contract;

public interface SelectCarContract {
    interface View {
        void showProgress();
        void showMessage(String message);
        void fetchData();
        void saveToDB();
        void moveToAddCarScreen();
    }
    interface Presenter {
        // validation methods
        // api calling method
    }
}
