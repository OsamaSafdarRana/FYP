package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.ShowHistoryContract;
import com.exercise.caraugmentedreality.Presenter.ShowHistoryPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.AddHistoryActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

public class ShowHistoryFragment extends BaseFragment implements ShowHistoryContract.View {

    @BindView(R.id.bt_back)
    ImageButton bt_back;
    @BindView(R.id.tv_engine_oil)
    TextView tvEngineOil;
    @BindView(R.id.tv_mileage)
    TextView tv_mileage;
    @BindView(R.id.tv_av_drive)
    TextView tv_av_drive;
    @BindView(R.id.tv_oil_thickness)
    TextView tv_oil_thickness;
    @BindView(R.id.bt_update)
    Button bt_update;

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

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveOnBack();
                }
            });
            bt_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToAddHistory();
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
    public void moveToAddHistory() {
        Intent intent = new Intent(getActivity(), AddHistoryActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void moveOnBack() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void showData() {
        try {
            journalRef.child(registNumber).child("SpeedometerReading").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        tv_mileage.setText(dataSnapshot.getValue().toString());
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
                        tv_av_drive.setText(dataSnapshot.getValue().toString());
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
                        tvEngineOil.setText(dataSnapshot.getValue().toString());
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
                        tv_oil_thickness.setText(dataSnapshot.getValue().toString());
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
