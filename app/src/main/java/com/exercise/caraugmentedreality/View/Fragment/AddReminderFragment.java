package com.exercise.caraugmentedreality.View.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.exercise.caraugmentedreality.Contract.AddReminderContract;
import com.exercise.caraugmentedreality.Presenter.AddReminderPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.EngineGuideActivity;
import com.exercise.caraugmentedreality.View.Activity.ExternalGuideActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.InternalGuideActivity;
import com.exercise.caraugmentedreality.View.Activity.ReminderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;

public class AddReminderFragment extends BaseFragment implements AddReminderContract.View {
    private AddReminderPresenter mPresenter;

    @BindView(R.id.bt_back)
    ImageView bt_back;
    @BindView(R.id.bt_continue)
    Button bt_continue;
    @BindView(R.id.et_service)
    AutoCompleteTextView et_service;
    @BindView(R.id.et_km)
    AutoCompleteTextView et_km;

    private FirebaseAuth mAuth;
    DatabaseReference reminderRef,userRef;
    String uid,registNumber;
    String service,km,notes;

    public AddReminderFragment() {
        this.mPresenter = new AddReminderPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        reminderRef = FirebaseDatabase.getInstance().getReference().child("Reminder").child(uid);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addreminder, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");

            String[] servicetype = getActivity().getResources().getStringArray(R.array.servicetype);
            ArrayAdapter<String> service_adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,servicetype);
            et_service.setAdapter(service_adapter);

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToReminderScreen();
                }
            });
            bt_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
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
    public void saveData() {
        service = et_service.getText().toString();
        km = et_km.getText().toString();

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    HashMap journalMap = new HashMap();

                    journalMap.put("RegistrationNumber", registNumber);
                    journalMap.put("ServiceType", service);
                    journalMap.put("KMs", km);
                    journalMap.put("Notes", notes);

                    reminderRef.child(registNumber).child(service).updateChildren(journalMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        showMessage("Reminder Added Successfully");
        moveToReminderScreen();
    }

    @Override
    public void moveToReminderScreen() {
        Intent intent = new Intent(getActivity(), ReminderActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    //        intent.putExtra("ServiceType",service);
//        intent.putExtra("KM",km);
    @Override
    public void moveToNotifications() {

    }
}
