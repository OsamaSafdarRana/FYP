package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.caraugmentedreality.Contract.TroubleshootContract;
import com.exercise.caraugmentedreality.Presenter.TroubleshootPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.ProblemActivity;
import com.exercise.caraugmentedreality.View.Activity.TroubleshootActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

import static android.view.View.GONE;

public class TroubleshootFragment extends BaseFragment implements TroubleshootContract.View, AdapterView.OnItemSelectedListener {

    private TroubleshootPresenter mPresenter;

    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.tv_question)
    TextView tv_question;
    @BindView(R.id.tv_ans_req)
    TextView tv_ans_req;
    @BindView(R.id.sp_ans)
    Spinner sp_ans;
    @BindView(R.id.bt_continue)
    Button bt_continue;
    @BindView(R.id.et_prob_st)
    EditText et_prob_st;
    @BindView(R.id.bt_prob_st)
    Button bt_prob_st;
    @BindView(R.id.bt_help)
    TextView bt_help;
    @BindView(R.id.tv_or)
    TextView tv_or;

    String problem="";
    String[] radiator_coolant = {"heat","heat up","heated","heated up","heating","heating up","hot",
            "warm","steam","fire","throttle feels heavy","heavy"};
    String[] engine_head = {"heat","heat up","heated","heated up","heating","heating up","hot",
            "warm","steam","fire","throttle feels heavy","heavy"};
    String[] battery = {"doesn't start","doesn't turn on","not starting","not turning on","carbon on terminals",
            "loose terminals","sound is different", "sound is down", "sound is wierd","carbon"};
    String[] fuel_guage = {"on E", "below E"};

    String[] questions = {"Is your car turning on?","Does your car support OBD-2?","Do you have OBD-2 device connected with your car?",
            "Do you see temperature needle going towards \"H\"","Can you see steam coming out of radiator?","Do you feel throttle getting heavy?",
            "Is your car picking self?","Does self's voice sound different?","Are terminals of battery connected properly and tightly?","Fluid level" +
            " of battery is okay?","Check fuel guage on speedometer and check if there is fuel in your car or not?"};

    public TroubleshootFragment(){mPresenter =new TroubleshootPresenter(this);}

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_troubleshoot,container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState==null){
            iv_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });

            bt_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(et_prob_st.getVisibility() == GONE){
                        moveToProblemScreen(problem);
                    }
                    else {
                        moveToWriteProblem();
                    }
                }
            });

            bt_prob_st.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_ans_req.setText(R.string.text_write);
                    tv_question.setVisibility(GONE);
                    sp_ans.setVisibility(GONE);
                    tv_or.setVisibility(GONE);
                    et_prob_st.setVisibility(View.VISIBLE);
                    bt_help.setVisibility(View.VISIBLE);
                    bt_prob_st.setVisibility(GONE);
                    bt_continue.setVisibility(View.VISIBLE);
                }
            });

            bt_help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("Help");
                }
            });

            sp_ans.setOnItemSelectedListener(this);
            tv_question.setText(questions[0]);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 1:
                if(tv_question.getText() == questions[0]) {
                    tv_question.setText(questions[3]);
                    sp_ans.setSelection(0);
                }
                else if(tv_question.getText() == questions[3]){
                    tv_question.setText(questions[4]);
                    sp_ans.setSelection(0);
                }
                else if(tv_question.getText() == questions[4]){
                    sp_ans.setSelection(0);
                    problem = "Possible Problematic Component: Radiator";
                    tv_question.setText(problem);
                    sp_ans.setVisibility(View.INVISIBLE);
                    tv_or.setVisibility(View.INVISIBLE);
                    bt_prob_st.setVisibility(View.INVISIBLE);
                    bt_continue.setVisibility(View.VISIBLE);
                }
                else if(tv_question.getText() == questions[5]){
                    sp_ans.setSelection(0);
                    problem = "Possible Problematic Component: Radiator";
                    tv_question.setText(problem);
                    sp_ans.setVisibility(View.INVISIBLE);
                    tv_or.setVisibility(View.INVISIBLE);
                    bt_prob_st.setVisibility(View.INVISIBLE);
                    bt_continue.setVisibility(View.VISIBLE);
                }
                else if(tv_question.getText() == questions[6]){
                    sp_ans.setSelection(0);
                    tv_question.setText(questions[7]);
                }
                else if(tv_question.getText() == questions[7]){
                    sp_ans.setSelection(0);
                    tv_question.setText(questions[8]);
                }
                else if(tv_question.getText() == questions[8]){
                    sp_ans.setSelection(0);
                    tv_question.setText(questions[9]);
                }
                else if(tv_question.getText() == questions[9]){
                    sp_ans.setSelection(0);
                    tv_question.setText(questions[10]);
                }
                else if(tv_question.getText() == questions[10]){
                    sp_ans.setSelection(0);
                    problem = "Car is facing issue with 'self'.";
                    tv_question.setText(problem);
                    tv_or.setVisibility(View.INVISIBLE);
                    sp_ans.setVisibility(View.INVISIBLE);
                    bt_prob_st.setVisibility(View.INVISIBLE);
                    bt_continue.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if(tv_question.getText() == questions[0]){
                    tv_question.setText(questions[6]);
                    sp_ans.setSelection(0);
                }
                else if(tv_question.getText() == questions[3]){
                    tv_question.setText(questions[4]);
                    sp_ans.setSelection(0);
                }
                else if(tv_question.getText() == questions[4]){
                    tv_question.setText(questions[5]);
                    sp_ans.setSelection(0);
                }
                else if(tv_question.getText() == questions[5]){
                    sp_ans.setSelection(0);
                    problem = "No Problem in Radiator. Might be an issue in wiring or computer or else.";
                    tv_question.setText(problem);
                    tv_or.setVisibility(View.INVISIBLE);
                    sp_ans.setVisibility(View.INVISIBLE);
                    bt_prob_st.setVisibility(View.INVISIBLE);
                }
                else if(tv_question.getText() == questions[6]){
                    tv_question.setText(questions[8]);
                    sp_ans.setSelection(0);
                }
                else if(tv_question.getText() == questions[7]){
                    tv_question.setText(questions[8]);
                    sp_ans.setSelection(0);
                }
                else if(tv_question.getText() == questions[8]){
                    sp_ans.setSelection(0);
                    problem = "Battery terminals are not connected properly. Tighten them up";
                    tv_question.setText(problem);
                    tv_or.setVisibility(View.INVISIBLE);
                    sp_ans.setVisibility(View.INVISIBLE);
                    bt_prob_st.setVisibility(View.INVISIBLE);
                    bt_continue.setVisibility(View.VISIBLE);
                }
                else if(tv_question.getText() == questions[9]){
                    sp_ans.setSelection(0);
                    problem = "Battery fluid level is low or empty. Fill it";
                    tv_question.setText(problem);
                    tv_or.setVisibility(View.INVISIBLE);
                    sp_ans.setVisibility(View.INVISIBLE);
                    bt_prob_st.setVisibility(View.INVISIBLE);
                    bt_continue.setVisibility(View.VISIBLE);
                }
                else if(tv_question.getText() == questions[10]){
                    sp_ans.setSelection(0);
                    problem = "Fuel tank is empty. Fill in from nearby Petrol pump.";
                    tv_question.setText(problem);
                    tv_or.setVisibility(View.INVISIBLE);
                    sp_ans.setVisibility(View.INVISIBLE);
                    bt_prob_st.setVisibility(View.INVISIBLE);
                    bt_continue.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    public void moveToProblemScreen(String issue) {
        Intent intent = new Intent(getActivity(), ProblemActivity.class);
        intent.putExtra("problem",issue);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void moveToWriteProblem() {
        if(!et_prob_st.getText().toString().isEmpty()){
            String problem_written = et_prob_st.getText().toString();
            for(int i=0; i<battery.length; i++) {
                if (problem_written.contains(battery[i])) {
                    problem = "Battery issue.";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<engine_head.length; i++) {
                if (problem_written.contains(engine_head[i])) {
                    problem = "Head heating up.";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<radiator_coolant.length; i++) {
                if (problem_written.contains(radiator_coolant[i])) {
                    problem = "Radiator heating up.";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<fuel_guage.length; i++) {
                if (problem_written.contains(fuel_guage[i])) {
                    problem = "Fuel tank is empty.";
                    moveToProblemScreen(problem);
                }
            }
        }
    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
