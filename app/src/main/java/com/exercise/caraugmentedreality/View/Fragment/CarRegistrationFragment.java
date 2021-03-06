package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.exercise.caraugmentedreality.Contract.CarRegistrationContract;
import com.exercise.caraugmentedreality.Presenter.CarRegistrationPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

public class CarRegistrationFragment extends BaseFragment implements CarRegistrationContract.View {

    private CarRegistrationPresenter mPresenter;

    @BindView(R.id.et_manufacturer)
    AutoCompleteTextView et_manufacturer;
    @BindView(R.id.et_model)
    AutoCompleteTextView et_model;
    @BindView(R.id.et_year)
    AutoCompleteTextView et_year;
    @BindView(R.id.et_variant)
    AutoCompleteTextView et_variant;
    @BindView(R.id.et_reg_no)
    AutoCompleteTextView et_reg_no;
    @BindView(R.id.sp_capacity)
    Spinner sp_capacity;
    @BindView(R.id.bt_continue)
    Button bt_continue;

    String[] capacity = {"Tap for Engine Capacity","660 CC","800 CC","1000 CC","1300 CC","1500 CC","1600 CC","1800 CC"};

    private FirebaseAuth mAuth;
    DatabaseReference userRef,carRef,carsRegiteredRef;
    String uid,regNo,manufacturer,model,year,variant,engineCapacity;

    ArrayList<String> regCarsList;

    public CarRegistrationFragment() {
        mPresenter = new CarRegistrationPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        carRef = FirebaseDatabase.getInstance().getReference().child("Cars").child(uid);
        carsRegiteredRef = FirebaseDatabase.getInstance().getReference().child("CarsRegistered");
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_car_registration,container,false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            String[] manufacturer = getActivity().getResources().getStringArray(R.array.manufacturer);
            String[] models = getActivity().getResources().getStringArray(R.array.models);
            String[] variants = getActivity().getResources().getStringArray(R.array.variants);

            regCarsList = new ArrayList<>();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_item2,
                    capacity);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item2);
            sp_capacity.setAdapter(adapter);

            ArrayAdapter<String> man_adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,manufacturer);
            et_manufacturer.setAdapter(man_adapter);

            ArrayAdapter<String> mod_adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,models);
            et_model.setAdapter(mod_adapter);

            ArrayAdapter<String> variant_adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,variants);
            et_variant.setAdapter(variant_adapter);

        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_reg_no.getText().toString().isEmpty()){
                    et_reg_no.setError("Registration Number required.");
                }
                else if(et_manufacturer.getText().toString().isEmpty()){
                    et_manufacturer.setError("Manufacturer required.");
                }
                else if( et_model.getText().toString().isEmpty()){
                    et_model.setError("Model required.");
                }
                else if(et_year.getText().toString().isEmpty()){
                    et_year.setError("Model Year required.");
                }
                else if(et_variant.getText().toString().isEmpty()){
                    et_variant.setError("Variant required.");
                }
                else if(sp_capacity.getSelectedItemPosition()==0){
                    showMessage("Select Engine Capacity");
                }
                else{
                    carsRegiteredRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                boolean flag =false;
//                                if(dataSnapshot.exists())
                                for(DataSnapshot regNoSnapshot: dataSnapshot.getChildren()){
                                    if(et_reg_no.getText().toString().equals(regNoSnapshot.getValue().toString())){
                                        et_reg_no.setError("Reg No already exists");
                                        flag = true;
                                        break;
                                    }
                                }
                                if(!flag){
                                    saveToDB();
                                }
                                //Aur agr nae para howa tou saveToDB() method call ho jae. Lekin puri array search honay k baad
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
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
    public void saveToDB() {
        regNo = et_reg_no.getText().toString();
        manufacturer = et_manufacturer.getText().toString();
        model = et_model.getText().toString();
        year = et_year.getText().toString();
        variant = et_variant.getText().toString();
        engineCapacity = sp_capacity.getSelectedItem().toString();

        String key = carsRegiteredRef.push().getKey();
        carsRegiteredRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists())
                    carsRegiteredRef.child(key).setValue(regNo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    HashMap carMap = new HashMap();
                        carMap.put("UserID",uid);
                        carMap.put("RegistrationNumber",regNo);
                        carMap.put("Manufacturer",manufacturer);
                        carMap.put("Model",model);
                        carMap.put("Year",year);
                        carMap.put("Variant",variant);
                        carMap.put("EngineCapacity",engineCapacity);
                    carRef.child(regNo).updateChildren(carMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()) {
                                showMessage("Data is stored successfully");
                                moveToHomeScreen();
                            }
                            else {
                                showMessage("Error while storing data");
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getRegNo() {

    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
//
//

//                    carRef.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                            showMessage(dataSnapshot.getChildren().toString());
//                            long a = dataSnapshot.getChildrenCount();
//                        }
//
//                        @Override
//                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


//if(registrationNo != null) {
//        regNo = database.getReference("Cars/"+ uid+"/"+registrationNo+"/Registration Number");
//        }
//        else{
//        et_reg_no.setError("Registration Number of your vehicle is required.");
//        }
//        if(man != null) {
//        manufacturer = database.getReference("Cars/"+uid+"/"+registrationNo+"/Details/Manufacturer");
//        }
//        else{
//        et_reg_no.setError("Manufacturer of your vehicle is required.");
//        }
//        if(mod != null) {
//        model = database.getReference("Cars/" + uid+"/"+registrationNo+"/Details/Model");
//        }
//        else{
//        et_reg_no.setError("Model of your vehicle is required.");
//        }
//        if(yr != null) {
//        year = database.getReference("Cars/"+ uid+"/"+registrationNo+"/Details/Year");
//        }
//        else{
//        et_reg_no.setError("Year of your vehicle is required.");
//        }
//        if(var != null) {
//        variant = database.getReference("Cars/"+ uid+"/"+registrationNo+"/Details/Variant");
//        }
//        else{
//        et_reg_no.setError("Variant of your vehicle is required.");
//        }
//
//        regNo.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(
//        DataSnapshot dataSnapshot) {
//        regNo.setValue(registrationNo);
//
//        }
//@Override
//public void onCancelled(DatabaseError databaseError) {
//        }
//        });
//
//        manufacturer.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(
//        DataSnapshot dataSnapshot) {
//        manufacturer.setValue(man);
//
//        }
//@Override
//public void onCancelled(DatabaseError databaseError) {
//        }
//        });
//
//        model.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(
//        DataSnapshot dataSnapshot) {
//        model.setValue(mod);
//
//        }
//@Override
//public void onCancelled(DatabaseError databaseError) {
//        }
//        });
//
//        year.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(
//        DataSnapshot dataSnapshot) {
//        year.setValue(yr);
//
//        }
//@Override
//public void onCancelled(DatabaseError databaseError) {
//        }
//        });
//
//        variant.addValueEventListener(new ValueEventListener() {
//@Override
//public void onDataChange(
//        DataSnapshot dataSnapshot) {
//        variant.setValue(var);
//
//        }
//@Override
//public void onCancelled(DatabaseError databaseError) {
//        }
//        });