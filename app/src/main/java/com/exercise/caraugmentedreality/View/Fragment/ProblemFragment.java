package com.exercise.caraugmentedreality.View.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.ProblemContract;
import com.exercise.caraugmentedreality.Presenter.ProblemPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.TroublshootOptionsActivity;

import butterknife.BindView;

public class ProblemFragment extends BaseFragment implements ProblemContract.View {

    private ProblemPresenter mPresenter;

    @BindView(R.id.bt_implement)
    Button bt_implement;
    @BindView(R.id.iv_problem)
    ImageView iv_problem;
    @BindView(R.id.tv_problem_statement)
    TextView tv_problem_statement;
    @BindView(R.id.tv_problem_statement_details)
    TextView tv_problem_statement_details;

    String problem = "";

    public ProblemFragment(){mPresenter = new ProblemPresenter(this);}

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_problem, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null) {
            problem = getActivity().getIntent().getStringExtra("problem");
            tv_problem_statement.setText(problem);
            if (problem.contains("Problematic Component: Radiator")) {
                iv_problem.setImageDrawable(getResources().getDrawable(R.drawable.ic_radiator_heat));
                tv_problem_statement_details.setText(R.string.text_radiator_details);
            }
            else if (problem.contains("Problematic Component: Battery")) {
                iv_problem.setImageDrawable(getResources().getDrawable(R.drawable.ic_battery));
                tv_problem_statement_details.setText(R.string.text_battery_details);
            }
            else if (problem.contains("Problematic Component: Engine Head")) {
                iv_problem.setImageDrawable(getResources().getDrawable(R.drawable.ic_engine));
                tv_problem_statement_details.setText(R.string.text_head_details);
            }
            else if(problem.contains("Fuel Tank")){
                iv_problem.setVisibility(View.GONE);
                bt_implement.setText("Troubleshoot Again");
                tv_problem_statement.setText("No fuel.");
                tv_problem_statement_details.setText(R.string.text_fueltank_details);
            }
            else if(problem.contains("Fuel Pump")){
                iv_problem.setVisibility(View.GONE);
                bt_implement.setText("Troubleshoot Again");
                tv_problem_statement.setText(problem);
                tv_problem_statement_details.setText(R.string.text_fuelpump_details);
            }
            else if(problem.contains("Fuel")){
                iv_problem.setVisibility(View.GONE);
                bt_implement.setText("Troubleshoot Again");
                tv_problem_statement.setText(problem+"Either there is no fuel or fuel injection to engine is problem.");
                tv_problem_statement_details.setText(R.string.text_fuel_details);
            }
            else if(problem.contains("Missing")){
                iv_problem.setVisibility(View.GONE);
                bt_implement.setText("Troubleshoot Again");
                tv_problem_statement.setText(problem);
                tv_problem_statement_details.setText(R.string.text_missing_details);
            }
            else {
                iv_problem.setVisibility(View.GONE);
                bt_implement.setText("Troubleshoot Again");
                tv_problem_statement.setText("Not a basic problem!");
                tv_problem_statement_details.setText(R.string.text_notbasic_details);
            }

            bt_implement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (problem.contains("Problematic Component: Radiator")) {
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.Car.CAR");
                        if (launchIntent != null) {
                            startActivity(launchIntent);
                        } else {
                            showMessage("There is no package available in android");
                        }
                    } else if (problem.contains("Problematic Component: Battery")) {
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.FYPCAR.CARBattery");
                        if (launchIntent != null) {
                            startActivity(launchIntent);
                        } else {
                            showMessage("There is no package available in android");
                        }
                    } else if (problem.contains("Problematic Component: Engine Head")) {
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.FYPCAR.CAREngine");
                        if (launchIntent != null) {
                            startActivity(launchIntent);
                        } else {
                            showMessage("There is no package available in android");
                        }
                    }
                    else if(problem.contains("Fuel Tank")){
                        moveToTroublshoot();
                    }
                    else if(problem.contains("Fuel Pump")){
                        moveToTroublshoot();
                    }
                    else if(problem.contains("Fuel")){
                        moveToTroublshoot();
                    }
                    else if(problem.contains("Missing")){
                        moveToTroublshoot();
                    }
                    else {
                        moveToTroublshoot();
                    }
//                    Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
//                    startActivity(intent);
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
    public void moveToTroublshoot() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
