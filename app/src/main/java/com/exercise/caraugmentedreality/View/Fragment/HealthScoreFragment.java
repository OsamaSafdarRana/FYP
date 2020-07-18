package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.HealthScoreContract;
import com.exercise.caraugmentedreality.Presenter.HealthScorePresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.AddHistoryActivity;
import com.exercise.caraugmentedreality.View.Activity.BaseActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.ShowHistoryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

public class HealthScoreFragment extends BaseFragment implements HealthScoreContract.View {

    HealthScorePresenter mPresenter;

    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.tv_healthscore)
    TextView tv_healthScore;
    @BindView(R.id.tv_clickhere)
    TextView tv_clickhere;
    @BindView(R.id.et_mileage)
    AutoCompleteTextView et_mileage;
    @BindView(R.id.bt_notification)
    ImageView bt_notification;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.bt_continue)
    Button bt_continue;

    String uid,registNumber,reading,avDrive,oil,oilThickness;

    private FirebaseAuth mAuth;
    DatabaseReference journalRef;

    public HealthScoreFragment(){
        mPresenter = new HealthScorePresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        journalRef = FirebaseDatabase.getInstance().getReference().child("Journal").child(uid);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health_score, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");
            showHealthScore();

            iv_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });
            bt_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMessage("No notifications");
                }
            });
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToJournal();
                }
            });
            bt_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHealthScore();
                }
            });
            tv_clickhere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToShowHistory();
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
    public void moveToJournal() {
        getActivity().finish();
    }

    @Override
    public int showHealthScore() {
        try {
            journalRef.child(registNumber).child("SpeedometerReading").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        reading = dataSnapshot.getValue().toString();
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
                        avDrive = dataSnapshot.getValue().toString();
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
                        oil = dataSnapshot.getValue().toString();
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
                        oilThickness = dataSnapshot.getValue().toString();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (NullPointerException NPexc){
            showMessage(NPexc.toString());
        }

        return 0;
    }

    @Override
    public void moveToShowHistory() {
        Intent intent = new Intent(getActivity(), ShowHistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void moveToNotifications() {

    }
}

//    String[] oil = {"1 month ago","2 months ago","3 months ago","More then 3 months ago"};
//    String[] fluid = {"3 days ago","1 week ago","2 weeks ago","More then 2 weeks ago"};

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference brakeFluid = database.getReference("Journal/Brake Fluid");
//    DatabaseReference engineCoolant = database.getReference("Journal/Engine Coolant");
//    DatabaseReference engineOil = database.getReference("Journal/Engine Oil");
//    DatabaseReference radiatorFluid = database.getReference("Journal/Radiator Fluid");
//    DatabaseReference transmissionFluid = database.getReference("Journal/Transmission Fluid");

//brakeFluid.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue().toString().equals(oil[0])) {
//                    bOilScore = 19;
//                } else if (dataSnapshot.getValue().toString().equals(oil[1])) {
//                    bOilScore = 14;
//                } else if (dataSnapshot.getValue().toString().equals(oil[2])) {
//                    bOilScore = 10;
//                } else {
//                    bOilScore = 5;
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        engineCoolant.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue().toString().equals(fluid[0])) {
//                    eCoolantScore = 9;
//                } else if (dataSnapshot.getValue().toString().equals(fluid[1])) {
//                    eCoolantScore = 7;
//                } else if (dataSnapshot.getValue().toString().equals(fluid[2])) {
//                    eCoolantScore = 5;
//                } else {
//                    eCoolantScore = 2;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        engineOil.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue().toString().equals(oil[0])) {
//                    eOilScore = 38;
//                } else if (dataSnapshot.getValue().toString().equals(oil[1])) {
//                    eOilScore = 28;
//                } else if (dataSnapshot.getValue().toString().equals(oil[2])) {
//                    eOilScore = 20;
//                } else {
//                    eOilScore = 10;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        radiatorFluid.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue().toString().equals(fluid[0])) {
//                    rFluidScore = 9;
//                } else if (dataSnapshot.getValue().toString().equals(fluid[1])) {
//                    rFluidScore = 7;
//                } else if (dataSnapshot.getValue().toString().equals(fluid[2])) {
//                    rFluidScore = 5;
//                } else {
//                    rFluidScore = 2;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//        transmissionFluid.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.getValue().toString().equals(oil[0])) {
//                    tFluidScore = 19;
//                } else if (dataSnapshot.getValue().toString().equals(oil[1])) {
//                    tFluidScore = 14;
//                } else if (dataSnapshot.getValue().toString().equals(oil[2])) {
//                    tFluidScore = 10;
//                } else {
//                    tFluidScore = 5;
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//        score = bOilScore + eCoolantScore + eOilScore + rFluidScore + tFluidScore;
//        return score;