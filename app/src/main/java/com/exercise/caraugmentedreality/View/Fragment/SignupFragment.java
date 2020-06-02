package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.SignupContract;
import com.exercise.caraugmentedreality.Model.User;
import com.exercise.caraugmentedreality.Presenter.SignupPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.CarRegistrationActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import butterknife.BindView;

public class SignupFragment extends BaseFragment implements SignupContract.View {

    SignupPresenter mPresenter;

    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.bt_continue)
    Button bt_signup;

    @BindView(R.id.pb_signup)
    ProgressBar pb_signup;

    private FirebaseAuth mAuth;
    FirebaseUser user;

    String email, password;

    public SignupFragment() {
        mPresenter = new SignupPresenter(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if (savedInstanceState == null) {

            bt_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    registerUser();
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
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("Email",email);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void moveToCarRegistration() {
        Intent intent = new Intent(getActivity(), CarRegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void verifyEmail() {
        user = mAuth.getCurrentUser();
        if (user.getEmail() != null) {
            if (user.isEmailVerified()) {
                showMessage("Email is Verified.");
                bt_signup.setClickable(true);
                moveToCarRegistration();
            }
        } else {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    showMessage("Verification Email is sent.");
                    if (user.isEmailVerified()) {
                        moveToCarRegistration();
                    }
                }
            });
        }
    }


    @Override
    public void registerUser() {
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
        pb_signup.setVisibility(View.VISIBLE);

         mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 pb_signup.setVisibility(View.GONE);
                 if(task.isSuccessful()){
                     mAuth.getCurrentUser().sendEmailVerification()
                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if(task.isSuccessful()){
                                         showMessage("User Registered Successfully. Please Check your Email for Verification.");
                                         et_email.getText().clear();
                                         et_password.getText().clear();
                                     }
                                     else {
                                         showMessage(task.getException().getMessage());
                                     }
                                 }
                             });
                 }
                 else {
                     if(task.getException() instanceof FirebaseAuthUserCollisionException){
                         showMessage("You are already registered");
                     }
                     else {
                         showMessage(task.getException().getMessage());
                     }
                 }
             }
         });

    }
}

//                    final User user = new User(email,password,username);
//
//                    users.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.child(user.getUsername()).exists()){
//                                showMessage("User already exists");
//                                et_email.getText().clear();
//                                et_password.getText().clear();
//                            }
//                            else {
//                                users.child(user.getUsername()).setValue(user);
//                                showMessage("User Added Successfully.");
//                                moveToHomeScreen();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
