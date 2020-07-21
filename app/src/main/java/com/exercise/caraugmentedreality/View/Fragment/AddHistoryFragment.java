package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.exercise.caraugmentedreality.Contract.AddHistoryContract;
import com.exercise.caraugmentedreality.Presenter.AddHistoryPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.ShowHistoryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class AddHistoryFragment extends BaseFragment implements AddHistoryContract.View, AdapterView.OnItemSelectedListener {

    AddHistoryPresenter mPresenter;

    @BindView(R.id.et_mileage)
    AutoCompleteTextView et_mileage;
    @BindView(R.id.et_av_drive)
    AutoCompleteTextView et_av_drive;
    @BindView(R.id.et_oil)
    AutoCompleteTextView et_oil;
    @BindView(R.id.sp_oil_thickness)
    Spinner sp_oil_thickness;
    @BindView(R.id.bt_continue)
    Button bt_continue;
    @BindView(R.id.bt_back)
    ImageButton bt_back;

    String[] oilThicknessarray = {"Tap to select","20W-50 (3000/4000)","10W-40 (5000)","5W-30 (9000)","0W-20 (9000)"};

    final Calendar myCalendar = Calendar.getInstance();

    private FirebaseAuth mAuth;
    DatabaseReference journalRef,userRef;
    String uid,registNumber,daysStr,healthscore,oilrunning,mileage,av_drive,engine_oil,oil_thickness;
    long noOfDays = 0;
    long score,noOfDaysLeft,scorevalue = 0;

    public AddHistoryFragment(){
        mPresenter = new AddHistoryPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        journalRef = FirebaseDatabase.getInstance().getReference().child("Journal").child(uid);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_history, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState ==  null){
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_item2,
                    oilThicknessarray);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item2);
            sp_oil_thickness.setAdapter(adapter);

            bt_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeData();
                }
            });
            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });

            DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel();
                }
            };
            et_oil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getActivity(), date3, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            sp_oil_thickness.setOnItemSelectedListener(this);
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
    public void moveToNotifications() {

    }

    @Override
    public void storeData() {
        mileage = et_mileage.getText().toString();
        av_drive = et_av_drive.getText().toString();
        engine_oil = et_oil.getText().toString();
        oil_thickness = sp_oil_thickness.getSelectedItem().toString();

        Long days = getNoOfDays();
        Long dailydrive = Long.parseLong(av_drive);
        Long running = Long.parseLong(oilrunning);

        score = (days*dailydrive);
        if(score<=1000){
            scorevalue = 90;
        }
        else if(score>1000 && score<=2000){
            scorevalue = 75;
        }
        else if(score>2000 && score<=3000){
            scorevalue = 60;
        }
        else {
            scorevalue = 40;
        }
        healthscore = healthscore.valueOf(scorevalue);

        if(dailydrive<running) {
            noOfDaysLeft = (running - score) / dailydrive;
        }
        else {
            showMessage("Change Oil");
        }
        daysStr = String.valueOf(noOfDaysLeft);

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    HashMap journalMap = new HashMap();

                    journalMap.put("UserID", uid);
                    journalMap.put("SpeedometerReading", mileage);
                    journalMap.put("AverageDrive", av_drive);
                    journalMap.put("EngineOil", engine_oil);
                    journalMap.put("OilThickness", oil_thickness);
                    journalMap.put("HealthScore", healthscore);
                    journalMap.put("DaysLeft", daysStr);

                    journalRef.child(registNumber).updateChildren(journalMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        showMessage("History Added Successfully");
        moveToHomeScreen();
    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
        getActivity().finish();
        startActivity(intent);
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_oil.setText(sdf.format(myCalendar.getTime()));
        getNoOfDays();
    }

    private long getNoOfDays() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.ENGLISH);

        String oil = sdf.format(myCalendar.getTime()); //string date
        String current = sdf.format(Calendar.getInstance().getTime());


        try {
            Date curentDate = sdf.parse(current);
            Date oilDate =  sdf.parse(oil);
            noOfDays = getUnitBetweenDates(oilDate, curentDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //showMessage(String.valueOf(noOfDays));
        return noOfDays;
        // use noOfDays var

    }

    private static long getUnitBetweenDates(Date oilDate, Date currentDate, TimeUnit unit) {
        long timeDiff = currentDate.getTime() - oilDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position == 0){
            oilrunning = "4000";
        }
        else if(position == 1){
            oilrunning = "5000";
        }
        else if(position == 2 || position == 3){
            oilrunning = "9000";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
