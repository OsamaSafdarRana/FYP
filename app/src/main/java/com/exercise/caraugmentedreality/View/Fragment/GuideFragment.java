package com.exercise.caraugmentedreality.View.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.exercise.caraugmentedreality.Contract.ExternalGuideContract;
import com.exercise.caraugmentedreality.Contract.GuideContract;
import com.exercise.caraugmentedreality.Presenter.GuidePresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.ExternalGuideActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.InternalGuideActivity;

import butterknife.BindView;

public class GuideFragment extends BaseFragment implements GuideContract.View {
    private GuidePresenter mPresenter;

    @BindView(R.id.iv_car)
    ImageView iv_car;

    @BindView(R.id.bt_notification)
    ImageButton bt_notification;

    @BindView(R.id.bt_exterior)
    Button bt_exterior;

    @BindView(R.id.bt_interior)
    Button bt_interior;

    @BindView(R.id.bt_help1)
    Button bt_help1;

    @BindView(R.id.bt_help2)
    Button bt_help2;

    public GuideFragment() {
        this.mPresenter = new GuidePresenter(this);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            iv_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });

            bt_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("No notifications");
                }
            });

            bt_interior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToInternalGuide();
                }
            });

            bt_exterior.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToExternalGuide();
                }
            });

            bt_help1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToHelp1();
                }
            });
            bt_help2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToHelp2();
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
    public void showError(String error) {

    }

    @Override
    public void showError(int error) {

    }

    @Override
    public void moveToInternalGuide() {
        Intent intent = new Intent(getActivity(), InternalGuideActivity.class);
        startActivity(intent);
    }

    @Override
    public void moveToExternalGuide() {
        Intent intent = new Intent(getActivity(), ExternalGuideActivity.class);
        startActivity(intent);
    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void moveToNotifications() {

    }

    @Override
    public void moveToHelp1() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("Interior")
                .setMessage("User can get complete guide of interior of his car. Interior Guide is giving " +
                        "information about\n1.Speedometer in detail.\n2. Check lights.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }

    @Override
    public void moveToHelp2() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("Exterior")
                .setMessage("User can get complete guide of exterior of his car. Exterior Guide has Car front, " +
                        "Car back guide and Car side guide which includes; Car Handles, Indicators, lights etc.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }
}
