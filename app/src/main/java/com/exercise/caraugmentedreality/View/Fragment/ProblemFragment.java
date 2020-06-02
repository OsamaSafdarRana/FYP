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

import com.exercise.caraugmentedreality.Contract.ProblemContract;
import com.exercise.caraugmentedreality.Presenter.ProblemPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;

import butterknife.BindView;

public class ProblemFragment extends BaseFragment implements ProblemContract.View {

    private ProblemPresenter mPresenter;

    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.bt_implement)
    Button bt_implement;
    @BindView(R.id.tv_problem_statement)
    TextView tv_problem_statement;

    String problem = "";

    public ProblemFragment(){mPresenter = new ProblemPresenter(this);}

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_problem, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            problem = getActivity().getIntent().getStringExtra("problem");
            tv_problem_statement.setText(problem);

            iv_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });

            bt_implement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Camera is opening");
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
    public void moveToImplementSolution() {

    }
}
