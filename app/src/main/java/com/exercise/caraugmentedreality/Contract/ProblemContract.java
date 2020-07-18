package com.exercise.caraugmentedreality.Contract;

public interface ProblemContract {

    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToHomeScreen();
        void moveToTroublshoot();
    }
    interface Presenter {

    }
}
