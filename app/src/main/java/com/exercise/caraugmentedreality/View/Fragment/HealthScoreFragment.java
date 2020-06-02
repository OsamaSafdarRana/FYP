package com.exercise.caraugmentedreality.View.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @BindView(R.id.tv_health_score)
    TextView tv_healthScore;

    @BindView(R.id.bt_notification)
    ImageView bt_notification;

    @BindView(R.id.iv_back)
    ImageView iv_back;

    @BindView(R.id.bt_update)
    Button bt_update;

    int bOilScore, eOilScore, rFluidScore, tFluidScore, eCoolantScore = 0;
    int score = 0;

    String uid,registNumber;

    String[] oil = {"1 month ago","2 months ago","3 months ago","More then 3 months ago"};
    String[] fluid = {"3 days ago","1 week ago","2 weeks ago","More then 2 weeks ago"};

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference brakeFluid = database.getReference("Journal/Brake Fluid");
    DatabaseReference engineCoolant = database.getReference("Journal/Engine Coolant");
    DatabaseReference engineOil = database.getReference("Journal/Engine Oil");
    DatabaseReference radiatorFluid = database.getReference("Journal/Radiator Fluid");
    DatabaseReference transmissionFluid = database.getReference("Journal/Transmission Fluid");

    public HealthScoreFragment(){
        mPresenter = new HealthScorePresenter(this);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_health_score, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            showHealthScore();
            displayScore();

            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");

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

    public void displayScore(){
            tv_healthScore.setText("68");
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
        brakeFluid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals(oil[0])) {
                    bOilScore = 19;
                } else if (dataSnapshot.getValue().toString().equals(oil[1])) {
                    bOilScore = 14;
                } else if (dataSnapshot.getValue().toString().equals(oil[2])) {
                    bOilScore = 10;
                } else {
                    bOilScore = 5;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        engineCoolant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals(fluid[0])) {
                    eCoolantScore = 9;
                } else if (dataSnapshot.getValue().toString().equals(fluid[1])) {
                    eCoolantScore = 7;
                } else if (dataSnapshot.getValue().toString().equals(fluid[2])) {
                    eCoolantScore = 5;
                } else {
                    eCoolantScore = 2;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        engineOil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals(oil[0])) {
                    eOilScore = 38;
                } else if (dataSnapshot.getValue().toString().equals(oil[1])) {
                    eOilScore = 28;
                } else if (dataSnapshot.getValue().toString().equals(oil[2])) {
                    eOilScore = 20;
                } else {
                    eOilScore = 10;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        radiatorFluid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals(fluid[0])) {
                    rFluidScore = 9;
                } else if (dataSnapshot.getValue().toString().equals(fluid[1])) {
                    rFluidScore = 7;
                } else if (dataSnapshot.getValue().toString().equals(fluid[2])) {
                    rFluidScore = 5;
                } else {
                    rFluidScore = 2;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        transmissionFluid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue().toString().equals(oil[0])) {
                    tFluidScore = 19;
                } else if (dataSnapshot.getValue().toString().equals(oil[1])) {
                    tFluidScore = 14;
                } else if (dataSnapshot.getValue().toString().equals(oil[2])) {
                    tFluidScore = 10;
                } else {
                    tFluidScore = 5;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        score = bOilScore + eCoolantScore + eOilScore + rFluidScore + tFluidScore;
        return score;
    }
    @Override
    public void moveToAddHistory() {
        Intent intent = new Intent(getActivity(), AddHistoryActivity.class);
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
