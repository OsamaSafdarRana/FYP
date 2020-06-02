package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.exercise.caraugmentedreality.Contract.AddHistoryContract;
import com.exercise.caraugmentedreality.Presenter.AddHistoryPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.JournalActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;

public class AddHistoryFragment extends BaseFragment implements AddHistoryContract.View, AdapterView.OnItemSelectedListener {

    AddHistoryPresenter mPresenter;

    @BindView(R.id.et_mileage)
    AutoCompleteTextView et_mileage;
    @BindView(R.id.et_av_drive)
    AutoCompleteTextView et_av_drive;
    @BindView(R.id.et_brake_fluid)
    AutoCompleteTextView et_brake_fluid;
    @BindView(R.id.et_coolant)
    AutoCompleteTextView et_coolant;
    @BindView(R.id.et_oil)
    AutoCompleteTextView et_oil;
    @BindView(R.id.et_radiator)
    AutoCompleteTextView et_radiator_fluid;
    @BindView(R.id.et_transmission)
    AutoCompleteTextView et_transmission_fluid;
    @BindView(R.id.sp_oil_running)
    Spinner sp_oil_running;
    @BindView(R.id.sp_oil_thickness)
    Spinner sp_oil_thickness;
    @BindView(R.id.bt_continue)
    Button bt_continue;

    final Calendar myCalendar = Calendar.getInstance();

    private FirebaseAuth mAuth;
    DatabaseReference journalRef,userRef;
    String uid,registNumber,mileage,av_drive,brake_fluid,coolant,radiator_fluid,engine_oil,transmission_fluid,oil_running,oil_thickness;

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
            showMessage(registNumber);

            bt_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeData();
                }
            });

            DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel1();
                }
            };
            DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel2();
                }
            };
            DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel3();
                }
            };
            DatePickerDialog.OnDateSetListener date4 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel4();
                }
            };
            DatePickerDialog.OnDateSetListener date5 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel5();
                }
            };
            et_brake_fluid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getActivity(), date1, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            et_coolant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getActivity(), date2, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            et_oil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getActivity(), date3, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            et_radiator_fluid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getActivity(), date4, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            et_transmission_fluid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(getActivity(), date5, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
            sp_oil_running.setOnItemSelectedListener(this);
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
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void moveToJournal() {
        Intent intent = new Intent(getActivity(), JournalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void storeData() {
        mileage = et_mileage.getText().toString();
        av_drive = et_av_drive.getText().toString();
        brake_fluid = et_brake_fluid.getText().toString();
        coolant = et_coolant.getText().toString();
        engine_oil = et_oil.getText().toString();
        radiator_fluid = et_radiator_fluid.getText().toString();
        transmission_fluid = et_transmission_fluid.getText().toString();
        oil_running = sp_oil_running.getSelectedItem().toString();
        oil_thickness = sp_oil_thickness.getSelectedItem().toString();

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    HashMap journalMap = new HashMap();

                    journalMap.put("UserID", uid);
                    journalMap.put("Mileage", mileage);
                    journalMap.put("AverageDrive", av_drive);
                    journalMap.put("BrakeFluid", brake_fluid);
                    journalMap.put("Coolant", coolant);
                    journalMap.put("EngineOil", engine_oil);
                    journalMap.put("RadiatorFluid", radiator_fluid);
                    journalMap.put("TransmissionFluid", transmission_fluid);
                    journalMap.put("OilRunning", oil_running);
                    journalMap.put("OilThickness", oil_thickness);

                    journalRef.child(registNumber).updateChildren(journalMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        showMessage("History Added Successfully");
        moveToJournal();
    }

    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_brake_fluid.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_coolant.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel3() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_oil.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel4() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_radiator_fluid.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel5() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_transmission_fluid.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(position != 0){
            showMessage("Selected: "+ item);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



//    DatabaseReference brakeFluid = database.getReference("Cars/" +regNo+"/Journal/Brake Fluid");
//    DatabaseReference engineCoolant = database.getReference("Cars/" +regNo+"/Journal/Engine Coolant");
//    DatabaseReference engineOil = database.getReference("Cars/" +regNo+"/Journal/Engine Oil");
//    DatabaseReference radiatorFluid = database.getReference("Cars/" +regNo+"/Journal/Radiator Fluid");
//    DatabaseReference transmissionFluid = database.getReference("Cars/" +regNo+"/Journal/Transmission Fluid");

//        brakeFluid.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(
//                    DataSnapshot dataSnapshot) {
//                        brakeFluid.setValue(et_brake_fluid.getSelectedItem().toString());
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        engineCoolant.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(
//                    DataSnapshot dataSnapshot) {
//                        engineCoolant.setValue(et_coolant.getSelectedItem().toString());
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        engineOil.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(
//                    DataSnapshot dataSnapshot) {
//                        engineOil.setValue(et_oil.getSelectedItem().toString());
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        radiatorFluid.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(
//                    DataSnapshot dataSnapshot) {
//                        radiatorFluid.setValue(et_radiator_fluid.getSelectedItem().toString());
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        transmissionFluid.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(
//                    DataSnapshot dataSnapshot) {
//                        transmissionFluid.setValue(et_transmission_fluid.getSelectedItem().toString());
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
