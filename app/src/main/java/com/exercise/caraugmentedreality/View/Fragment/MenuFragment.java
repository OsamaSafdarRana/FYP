package com.exercise.caraugmentedreality.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.exercise.caraugmentedreality.Contract.MenuContract;
import com.exercise.caraugmentedreality.Presenter.MenuPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.CarRegistrationActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.LoginActivity;
import com.exercise.caraugmentedreality.View.Activity.CarsActivity;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;

public class MenuFragment extends BaseFragment implements MenuContract.View {

    @BindView(R.id.iv_logout)
    ImageView iv_logout;

    @BindView(R.id.iv_addcar)
    ImageView iv_addcar;

    @BindView(R.id.iv_selectcar)
    ImageView iv_selectcar;

    @BindView(R.id.bt_back)
    ImageButton bt_back;

    private MenuPresenter mPresenter;

    public MenuFragment() {
        mPresenter = new MenuPresenter(this);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu,container,false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            iv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToLogin();
                }
            });

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });
            iv_addcar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToAddCar();
                }
            });
            iv_selectcar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToSelectCar();
                }
            });
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    @Override
    public void moveToAddCar() {
        Intent intent= new Intent(getActivity(), CarRegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void moveToViewCar() {

    }

    @Override
    public void moveToSelectCar() {
        Intent intent= new Intent(getActivity(), CarsActivity.class);
        startActivity(intent);
    }

    @Override
    public void moveToLogin() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
