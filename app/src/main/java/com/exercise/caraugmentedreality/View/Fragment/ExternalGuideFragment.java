package com.exercise.caraugmentedreality.View.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.ExternalGuideContract;
import com.exercise.caraugmentedreality.Presenter.ExternalGuidePresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;

import butterknife.BindView;

public class ExternalGuideFragment extends BaseFragment implements ExternalGuideContract.View {

    ExternalGuidePresenter mPresenter;

    @BindView(R.id.iv_car)
    ImageView iv_car;

    @BindView(R.id.bt_back)
    ImageButton bt_back;

    @BindView(R.id.tv_choose_option)
    TextView tv_choose;

    @BindView(R.id.bt_notification)
    ImageView bt_notification;

    @BindView(R.id.iv_side)
    ImageView iv_side;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.iv_front)
    ImageView iv_front;

    public ExternalGuideFragment(){
        mPresenter = new ExternalGuidePresenter();
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_external_guide, container, false);
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

            iv_side.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Side");
                    iv_side.setVisibility(View.INVISIBLE);
                    iv_back.setVisibility(View.INVISIBLE);
                    iv_front.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_side);
                    bt_back.setVisibility(View.VISIBLE);
                }
            });

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Back");
                    iv_side.setVisibility(View.INVISIBLE);
                    iv_back.setVisibility(View.INVISIBLE);
                    iv_front.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_back);
                    bt_back.setVisibility(View.VISIBLE);
                }
            });

            iv_front.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Front");
                    iv_side.setVisibility(View.INVISIBLE);
                    iv_back.setVisibility(View.INVISIBLE);
                    iv_front.setVisibility(View.INVISIBLE);
                    tv_choose.setText(R.string.text_front);
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
        iv_side.setVisibility(View.VISIBLE);
        iv_back.setVisibility(View.VISIBLE);
        iv_front.setVisibility(View.VISIBLE);
        tv_choose.setText(R.string.tv_choose_option);
        bt_back.setVisibility(View.INVISIBLE);
    }

    @Override
    public void moveToNotifications() {

    }
}
