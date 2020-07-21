package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.LoginContract;
import com.exercise.caraugmentedreality.Presenter.LoginPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;

public class LoginFragment extends BaseFragment implements LoginContract.View {

//    FirebaseDatabase database;
//    DatabaseReference users;

    @BindView(R.id.tv_signup)
    TextView tv_signin;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.bt_continue)
    Button bt_signin;
    @BindView(R.id.pb_signin)
    ProgressBar pb_signin;

    String email,password,name;

    private FirebaseAuth mAuth;

    private LoginPresenter mPresenter;

    public LoginFragment(){
        mPresenter = new LoginPresenter(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if (savedInstanceState == null) {
            bt_signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });

            tv_signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToSignup();
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
    public void signIn() {
        email = et_email.getText().toString();
        password = et_password.getText().toString();

        if(email.isEmpty()){
            et_email.setError("Email is required.");
            et_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please enter a valid Email.");
            et_password.requestFocus();
            return;
        }
        if(password.isEmpty()){
            et_password.setError("Password is required.");
            et_password.requestFocus();
            return;
        }
        if(password.length()<6){
            et_password.setError("Minimum Length of password should be 6");
            et_password.requestFocus();
            return;
        }
        pb_signin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pb_signin.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    moveToSelectCar();
                }
                else{
                    showMessage(task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            getActivity().finish();
            moveToSelectCar();
        }
    }

    @Override
    public void moveToSelectCar() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void moveToSignup() {
        Intent intent = new Intent(getActivity(), SignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}



//        users.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child(username).exists()){
//                    if(!username.isEmpty()){
//                        User login = dataSnapshot.child(username).getValue(User.class);
//                        if(login.getPassword().equals(password)){
//                            showMessage("Successfully Login.");
//                            moveToHomeScreen();
//                        }
//                        else {
//                            showMessage("Password is wrong.");
//                        }
//                    }
//                    else {
//                        showMessage("User is not registered.");
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });