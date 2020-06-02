package com.exercise.caraugmentedreality.View.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;

import butterknife.BindView;

public class HelpFragment extends BaseFragment {

    @BindView(R.id.iv_car)
    ImageView ivCar;

    @BindView(R.id.bt_continue)
    Button btContinue;

    @BindView(R.id.tv_help)
    TextView tvHelp;

    Boolean IS_TS;

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            Bundle bundle = getActivity().getIntent().getExtras();
//
//            if (getArguments() != null) {
//                IS_TS = getArguments().getBoolean(HomeFragment.IS_TS);
//
//                if (IS_TS.equals(true)) {
//                    String ts =bundle.getString("Troubleshoot");
//                    tvHelp.setText("It is troubleshooting");
//                } else {
//                    tvHelp.setText("Not troubleshooting");
//                }
//            }
            ivCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHome();
                }
            });

            btContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveOnContinue();
                }
            });
        }
    }

    public void moveToHome(){
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }

    public void moveOnContinue(){
        getActivity().finish();
    }
}
