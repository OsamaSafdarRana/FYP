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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import static android.view.View.INVISIBLE;

public class TroubleshootFragment extends BaseFragment implements TroubleshootContract.View {

    private TroubleshootPresenter mPresenter;

    @BindView(R.id.tv_question)
    TextView tv_question;
    @BindView(R.id.rg_ans)
    RadioGroup rg_ans;
    @BindView(R.id.radio_yes)
    RadioButton radio_yes;
    @BindView(R.id.radio_no)
    RadioButton radio_no;
    @BindView(R.id.bt_continue)
    Button bt_continue;
    @BindView(R.id.tv_write)
    TextView tv_write;
    @BindView(R.id.et_problem)
    AutoCompleteTextView et_problem;

    String problem="";
    String[] radiator_coolant = {"heat","heat up","heated","heated up","heating","heating up","hot",
            "warm","steam from radiator","fire","throttle feels heavy","heavy","radiator heating up"};
    String[] engine_head = {"heat","heat up","heated","heated up","heating","heating up","hot",
            "warm","steam from engine head","fire","throttle feels heavy","heavy","engine heating up"};
    String[] battery = {"doesn't start","doesn't turn on","not starting","not turning on","carbon on terminals",
            "loose terminals","sound is different", "sound is down", "sound is wierd","carbon"};
    String[] self = {"not picking self","no self's voice","no starting sound","not speaking"};
    String[] missing = {"jerk","jerks","jerking","missing","not a heating problem","no heating"};
    String[] fuel_guage = {"on E", "below E","starting and suddenly stoping","starting and stoping in a moment",
            "staring for a second and then turning off","staring for a second and then stopping"};

    String[] questions = {"Is your car turning on?","Do you see temperature needle going towards/appraoching/above \"H\"",
            "Do you feel throttle getting heavy?", "Do you see steam coming out of radiator?","Can you see steam coming out of Engine head?",
            "Is your car picking self?","Does self's voice sound different/low or sounds like \"Trrr\"?","Do you see check-lights in " +
            "dashboard getting dim?","Fluid level of battery is low?","Is your car turning on for a second and then automatically turning off?",
            "Check fuel guage on speedometer and check if fuel needle near/on \"E\""};

    public TroubleshootFragment(){mPresenter =new TroubleshootPresenter(this);}

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_troubleshoot,container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState==null){
            tv_question.setText(questions[0]);
            tv_write.setText(R.string.text_write_yourself);
            radio_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v);
                }
            });
            radio_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v);
                }
            });
            bt_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(et_problem.getVisibility() == INVISIBLE){
                        if(bt_continue.getAlpha() == 1)
                            moveToProblemScreen(problem);
                        else
                            showMessage("Complete Questionnaire first!");
                    }
                    else {
                        moveToWriteProblem();
                    }
                }
            });
            tv_write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tv_write.getText().toString().equals("Type your Problem")){
                        tv_write.setText(R.string.text_solvequestionnaire);
                        tv_question.setText(R.string.text_write);
                        rg_ans.setVisibility(View.INVISIBLE);
                        et_problem.setVisibility(View.VISIBLE);
                        bt_continue.setAlpha(1);
                    }
                    else {
                        tv_write.setText(R.string.text_write_yourself);
                        tv_question.setText(questions[0]);
                        rg_ans.setVisibility(View.VISIBLE);
                        et_problem.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha((float) 0.5);
                    }
                }
            });
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_yes:
                if (checked) {
                    if (tv_question.getText() == questions[0]) {
                        tv_question.setText(questions[1]);
                        rg_ans.clearCheck();
                    } else if (tv_question.getText() == questions[1]) {
                        tv_question.setText(questions[2]);
                        rg_ans.clearCheck();
                    } else if (tv_question.getText() == questions[2]) {
                        tv_question.setText(questions[3]);
                        rg_ans.clearCheck();
                    } else if (tv_question.getText() == questions[3]) {
                        rg_ans.clearCheck();
                        problem = "Problematic Component: Radiator";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    } else if (tv_question.getText() == questions[4]) {
                        rg_ans.clearCheck();
                        problem = "Problematic Component: Engine Head";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    } else if (tv_question.getText() == questions[5]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[6]);
                    } else if (tv_question.getText() == questions[6]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[7]);
                    } else if (tv_question.getText() == questions[7]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[8]);
                    } else if (tv_question.getText() == questions[8]) {
                        rg_ans.clearCheck();
                        problem = "Problematic Component: Battery (Hint: Battery fluid level is low).";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    } else if (tv_question.getText() == questions[9]) {
                        tv_question.setText(questions[10]);
                    } else if (tv_question.getText() == questions[10]) {
                        rg_ans.clearCheck();
                        problem = "Fuel Tank is Empty.";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    }
                }
                break;
            case R.id.radio_no:
                if (checked) {
                    if (tv_question.getText() == questions[0]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[5]);
                    } else if (tv_question.getText() == questions[1]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[2]);
                    } else if (tv_question.getText() == questions[2]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[3]);
                    } else if (tv_question.getText() == questions[3]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[4]);
                    } else if (tv_question.getText() == questions[4]) {
                        rg_ans.clearCheck();
                        problem = "Your car is doing Missing either because of some fuses or electric wiring or engine plugs.";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    } else if (tv_question.getText() == questions[5]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[9]);
                    } else if (tv_question.getText() == questions[6]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[9]);
                    } else if (tv_question.getText() == questions[7]) {
                        rg_ans.clearCheck();
                        tv_question.setText(questions[8]);
                    } else if (tv_question.getText() == questions[8]) {
                        rg_ans.clearCheck();
                        problem = "Does not seem to be a battery problem. Check battery terminals for confirmation.\n" +
                                "Otherwise Contact nearby mechanic.";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    } else if (tv_question.getText() == questions[9]) {
                        rg_ans.clearCheck();
                        problem = "Problem in the Self of car";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    } else if (tv_question.getText() == questions[10]) {
                        rg_ans.clearCheck();
                        problem = "Looks like your car is not injecting petrol at all. Reason can be:\n1- Fuel Pump\n2-Fuel Needle not working properly" +
                                "\n3-Fuel pipe is not clean or leaked.";
                        tv_question.setText(problem);
                        rg_ans.setVisibility(View.INVISIBLE);
                        bt_continue.setAlpha(1);
                        bt_continue.setClickable(true);
                    }
                }
                break;
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
    public void moveToProblemScreen(String issue) {
        Intent intent = new Intent(getActivity(), ProblemActivity.class);
        intent.putExtra("problem",issue);
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void moveToWriteProblem() {
        if(!et_problem.getText().toString().isEmpty()){
            String problem_written = et_problem.getText().toString();
            for(int i=0; i<battery.length; i++) {
                if (problem_written.contains(battery[i])) {
                    problem = "Problematic Component: Battery";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<engine_head.length; i++) {
                if (problem_written.contains(engine_head[i])) {
                    problem = "Problematic Component: Engine Head";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<radiator_coolant.length; i++) {
                if (problem_written.contains(radiator_coolant[i])) {
                    problem = "Problematic Component: Radiator";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<fuel_guage.length; i++) {
                if (problem_written.contains(fuel_guage[i])) {
                    problem = "Problem is relating to Fuel";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<self.length; i++) {
                if (problem_written.contains(self[i])) {
                    problem = "Problem is relating to Self of your car";
                    moveToProblemScreen(problem);
                }
            }
            for(int i=0; i<missing.length; i++) {
                if (problem_written.contains(missing[i])) {
                    problem = "Your car is doing missing.";
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



//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//        switch (position){
//            case 1:
//                if(tv_question.getText() == questions[0]) {
//                    tv_question.setText(questions[1]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[1]){
//                    tv_question.setText(questions[2]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[2]){
//                    tv_question.setText(questions[3]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[3]){
//                    sp_ans.setSelection(0);
//                    problem = "Problematic Component: Radiator";
//                    tv_question.setText(problem);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                else if(tv_question.getText() == questions[4]){
//                    sp_ans.setSelection(0);
//                    problem = "Problematic Component: Engine Head";
//                    tv_question.setText(problem);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                else if(tv_question.getText() == questions[5]){
//                    sp_ans.setSelection(0);
//                    tv_question.setText(questions[6]);
//                }
//                else if(tv_question.getText() == questions[6]){
//                    sp_ans.setSelection(0);
//                    tv_question.setText(questions[7]);
//                }
//                else if(tv_question.getText() == questions[7]){
//                    sp_ans.setSelection(0);
//                    tv_question.setText(questions[8]);
//                }
//                else if(tv_question.getText() == questions[8]){
//                    sp_ans.setSelection(0);
//                    problem = "Problematic Component: Battery (Hint: Battery fluid level is low).";
//                    tv_question.setText(problem);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                else if(tv_question.getText() == questions[9]){
//                    sp_ans.setSelection(0);
//                    tv_question.setText(questions[10]);
//                }
//                else if(tv_question.getText() == questions[10]){
//                    sp_ans.setSelection(0);
//                    problem = "Fuel Tank is Empty.";
//                    tv_question.setText(problem);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                break;
//            case 2:
//                if(tv_question.getText() == questions[0]){
//                    tv_question.setText(questions[5]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[1]){
//                    sp_ans.setSelection(0);
//                    tv_question.setText(questions[2]);
//                }
//                else if(tv_question.getText() == questions[2]){
//                    tv_question.setText(questions[3]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[3]){
//                    tv_question.setText(questions[4]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[4]){
//                    sp_ans.setSelection(0);
//                    problem = "Your car is doing Missing either because of some fuses or electric wiring or engine plugs.";
//                    tv_question.setText(problem);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                else if(tv_question.getText() == questions[5]){
//                    sp_ans.setSelection(0);
//                    tv_question.setText(questions[9]);
//                }
//                else if(tv_question.getText() == questions[6]){
//                    tv_question.setText(questions[9]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[7]){
//                    tv_question.setText(questions[8]);
//                    sp_ans.setSelection(0);
//                }
//                else if(tv_question.getText() == questions[8]){
//                    sp_ans.setSelection(0);
//                    problem = "Does not seem to be a battery problem. Check battery terminals for confirmation.\n" +
//                            "Otherwise Contact nearby mechanic.";
//                    tv_question.setText(problem);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                else if(tv_question.getText() == questions[9]){
//                    sp_ans.setSelection(0);
//                    problem = "Problem in the Self of car";
//                    tv_question.setText(problem);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                else if(tv_question.getText() == questions[10]){
//                    sp_ans.setSelection(0);
//                    problem = "Looks like your car is not injecting petrol at all. Reason can be:\n1- Fuel Pump\n2-Fuel Needle not working properly" +
//                            "\n3-Fuel pipe is not clean or leaked.";
//                    tv_question.setText(problem);
//                    tv_or.setVisibility(View.INVISIBLE);
//                    sp_ans.setVisibility(View.INVISIBLE);
//                    tv_ans_req.setVisibility(View.GONE);
//                    bt_help.setVisibility(View.GONE);
//                    bt_prob_st.setVisibility(View.INVISIBLE);
//                    bt_continue.setVisibility(View.VISIBLE);
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }