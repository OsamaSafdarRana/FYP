package com.exercise.caraugmentedreality.Contract;

public interface TroubleshootContract {
    interface View{
        void showProgress();
        void showMessage(String message);
        void showError(String error);
        void showError(int error);
        void moveToProblemScreen(String issue);
        void moveToWriteProblem();
        void moveToHomeScreen();
    }
    interface Presenter {

    }
}
