package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.ShowHistoryContract;
import com.exercise.caraugmentedreality.Presenter.ShowHistoryPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.JournalActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;

public class ShowHistoryFragment extends BaseFragment implements ShowHistoryContract.View {

    @BindView(R.id.bt_back)
    ImageButton bt_back;
    @BindView(R.id.bt_notification)
    ImageView bt_notification;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.tv_brake_oil)
    TextView tvBrakeOil;
    @BindView(R.id.tv_engine_coolant)
    TextView tvEngineCoolant;
    @BindView(R.id.tv_engine_oil)
    TextView tvEngineOil;
    @BindView(R.id.tv_radiator_fluid)
    TextView tvRadiatorFluid;
    @BindView(R.id.tv_transmission_fluid)
    TextView tvTransmissionFluid;
    @BindView(R.id.tv_mileage)
    TextView tv_mileage;
    @BindView(R.id.tv_av_drive)
    TextView tv_av_drive;
    @BindView(R.id.tv_oil_running)
    TextView tv_oil_running;
    @BindView(R.id.tv_oil_thickness)
    TextView tv_oil_thickness;

    ShowHistoryPresenter mPresenter;

    String uid,registNumber;

    private FirebaseAuth mAuth;
    DatabaseReference journalRef;

    public ShowHistoryFragment(){
        mPresenter = new ShowHistoryPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        journalRef = FirebaseDatabase.getInstance().getReference().child("Journal").child(uid);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_history,container,false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if (savedInstanceState == null){
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");

            showData();

            iv_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });
            bt_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToNotifications();
                }
            });
            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveOnBack();
                }
            });
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showError(int error) {

    }

    @Override
    public void moveToNotifications() {
        showMessage("No notifications at the moment");
    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void moveOnBack() {
        Intent intent = new Intent(getActivity(), JournalActivity.class);
        startActivity(intent);
    }

    @Override
    public void showData() {
        try {
            journalRef.child(registNumber).child("Mileage").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tv_mileage.setText("\tMileage of car:\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("AverageDrive").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tv_av_drive.setText("\tAverage Drive in a week:\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("BrakeFluid").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tvBrakeOil.setText("\tBrake Fluid (Last Changed):\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("Coolant").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tvEngineCoolant.setText("\tEngine Coolant (Last Changed):\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("EngineOil").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tvEngineOil.setText("\tEngine Oil (Last Changed):\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("RadiatorFluid").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tvRadiatorFluid.setText("\tRadiator Fluid (Last Changed):\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("TransmissionFluid").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tvTransmissionFluid.setText("\tTransmission Fluid (Last Changed):\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("OilRunning").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tv_oil_running.setText("\tExpected Running of Engine Oil:\n"+dataSnapshot.getValue().toString()+" km");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            journalRef.child(registNumber).child("OilThickness").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tv_oil_thickness.setText("\tThickness of Engine Oil:\n"+dataSnapshot.getValue().toString());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (NullPointerException NPexc){
            showMessage(NPexc.toString());
        }
    }
}
