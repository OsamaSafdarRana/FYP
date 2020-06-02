package com.exercise.caraugmentedreality.View.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.InternalGuideContract;
import com.exercise.caraugmentedreality.Presenter.InternalGuidePresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;

import butterknife.BindView;

public class InternalGuideFragment extends BaseFragment implements InternalGuideContract.View {

    private InternalGuidePresenter mInternalGuidePresenter;

    @BindView(R.id.iv_car)
    ImageView iv_car;

    @BindView(R.id.tv_choose_option)
    TextView tv_choose;

    @BindView(R.id.bt_notification)
    ImageView bt_notification;

    @BindView(R.id.iv_speedometer)
    ImageView iv_speedometer;

    @BindView(R.id.iv_battery)
    ImageView iv_battery;

    @BindView(R.id.iv_checkengine)
    ImageView iv_checkengine;

    @BindView(R.id.iv_handbrake)
    ImageView iv_handbrake;

    @BindView(R.id.iv_oil)
    ImageView iv_oil;

    @BindView(R.id.bt_back)
    ImageButton bt_back;

    public InternalGuideFragment(){
        mInternalGuidePresenter = new InternalGuidePresenter(this);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_internal_guide, container, false);
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

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveOnBack();
                }
            });

            iv_speedometer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Speedometer");
                    iv_speedometer.setVisibility(View.INVISIBLE);
                    iv_battery.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    iv_oil.setVisibility(View.INVISIBLE);
                    iv_checkengine.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_speedometer);
                    bt_back.setVisibility(View.VISIBLE);
                }
            });

            iv_battery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Battery Check Sign");
                    iv_speedometer.setVisibility(View.INVISIBLE);
                    iv_battery.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    iv_oil.setVisibility(View.INVISIBLE);
                    iv_checkengine.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_battery);
                    bt_back.setVisibility(View.VISIBLE);
                }
            });

            iv_checkengine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Engine Check Light");
                    iv_speedometer.setVisibility(View.INVISIBLE);
                    iv_battery.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    iv_oil.setVisibility(View.INVISIBLE);
                    iv_checkengine.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_check_light);
                    bt_back.setVisibility(View.VISIBLE);
                }
            });


            iv_oil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Engine Oil Level Light");
                    iv_speedometer.setVisibility(View.INVISIBLE);
                    iv_battery.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    iv_oil.setVisibility(View.INVISIBLE);
                    iv_checkengine.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_oil_check);
                    bt_back.setVisibility(View.VISIBLE);
                }
            });


            iv_handbrake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Hand Brake Light");
                    iv_speedometer.setVisibility(View.INVISIBLE);
                    iv_battery.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    iv_oil.setVisibility(View.INVISIBLE);
                    iv_checkengine.setVisibility(View.INVISIBLE);
                    iv_handbrake.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_handbrake_light);
                    bt_back.setVisibility(View.VISIBLE);
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
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void moveOnBack() {
        iv_speedometer.setVisibility(View.VISIBLE);
        iv_battery.setVisibility(View.VISIBLE);
        iv_handbrake.setVisibility(View.VISIBLE);
        iv_oil.setVisibility(View.VISIBLE);
        iv_checkengine.setVisibility(View.VISIBLE);
        iv_handbrake.setVisibility(View.VISIBLE);
        tv_choose.setText(R.string.tv_choose_option);
        bt_back.setVisibility(View.INVISIBLE);
    }

    @Override
    public void moveToNotifications() {

    }
}
