package com.exercise.caraugmentedreality.View.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.cocosw.bottomsheet.BottomSheet;
import com.exercise.caraugmentedreality.Contract.HomeContract;
import com.exercise.caraugmentedreality.Model.Car;
import com.exercise.caraugmentedreality.Presenter.HomePresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.AddHistoryActivity;
import com.exercise.caraugmentedreality.View.Activity.AddReminderActivity;
import com.exercise.caraugmentedreality.View.Activity.CarRegistrationActivity;
import com.exercise.caraugmentedreality.View.Activity.CarsActivity;
import com.exercise.caraugmentedreality.View.Activity.EngineGuideActivity;
import com.exercise.caraugmentedreality.View.Activity.ExternalGuideActivity;
import com.exercise.caraugmentedreality.View.Activity.InternalGuideActivity;
import com.exercise.caraugmentedreality.View.Activity.LoginActivity;
import com.exercise.caraugmentedreality.View.Activity.NotificationActivity;
import com.exercise.caraugmentedreality.View.Activity.ReminderActivity;
import com.exercise.caraugmentedreality.View.Activity.ShowHistoryActivity;
import com.exercise.caraugmentedreality.View.Activity.TroubleshootActivity;
import com.github.anastr.speedviewlib.TubeSpeedometer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements HomeContract.View {

    private HomePresenter mPresenter;

    @BindView(R.id.bt_notification)
    ImageButton bt_notification;
    @BindView(R.id.bt_menu)
    ImageButton bt_menu;
    @BindView(R.id.bt_troubleshoot)
    Button bt_troubleshoot;
    @BindView(R.id.bt_journal)
    Button bt_journal;
    @BindView(R.id.bt_guide)
    Button bt_guide;
    @BindView(R.id.bt_profile)
    Button bt_profile;
    @BindView(R.id.sp_cars)
    androidx.appcompat.widget.AppCompatSpinner sp_cars;

    private FirebaseAuth mAuth;
    List<Car> carList;
    List<String> carRegistration;

    String uid, email;
    String registNumber;
    int score;
    String scoreFromAdd,scorestr;
    DatabaseReference userRef, carsRef,journalRef;


    public HomeFragment() {
        mPresenter = new HomePresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        carsRef = FirebaseDatabase.getInstance().getReference().child("Cars").child(uid);
        journalRef = FirebaseDatabase.getInstance().getReference().child("Journal").child(uid);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if (savedInstanceState == null) {
            int lastSelectedItem= sp_cars.getSelectedItemPosition();
            sp_cars.setSelection(lastSelectedItem);

            carList = new ArrayList<Car>();
            carRegistration = new ArrayList<String>();
//            scoreFromAdd = getActivity().getIntent().getStringExtra("Score");
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");

            carsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //carList.clear();
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        Car car = postSnapshot.getValue(Car.class);
                        carList.add(car);
                    }

                    for (int i = 0; i < carList.size(); i++) {
                        carRegistration.add(carList.get(i).getRegistrationNumber());
                    }
                    carRegistration.add("Add Another car");

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            R.layout.spinner_item,
                            carRegistration);
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    sp_cars.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    showMessage("Error: "+databaseError);
                }
            });
            sp_cars.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    registNumber = item;
                    showHealthScore();
                    if(position == carList.size()){
                        Intent intent = new Intent(getActivity(), CarRegistrationActivity.class);
                        sp_cars.setSelection(0);
                        startActivity(intent);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            bt_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenu();
                }
            });

            bt_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToNotifications();
                }
            });
            bt_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new BottomSheet.Builder(getActivity()).title("Profile").sheet(R.menu.menu_profile).listener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case R.id.logout:
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    break;
                                case R.id.cars:
                                    Intent intent2 = new Intent(getActivity(), CarsActivity.class);
                                    startActivity(intent2);
                                    break;
                            }
                        }
                    }).show();
                }
            });

            bt_troubleshoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToTroubleshooting();
                }
            });
            bt_journal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToJournal();
                }
            });
            bt_guide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bt_guide.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moveToGuide();
                        }
                    });
                }
            });
        }

    }

    private void showHealthScore() {
        TubeSpeedometer speedView = getActivity().findViewById(R.id.speedView);
        try {
            journalRef.child(registNumber).child("HealthScore").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//                        if(scoreFromAdd != null){
//                            score = Integer.parseInt(scoreFromAdd);
//                            speedView.setSpeedAt(score);
//                        }
//                        else {
                            scorestr = dataSnapshot.getValue().toString();
                            score = Integer.parseInt(scorestr);
                            speedView.setSpeedAt(score);
                        //}
                            speedView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (score != 0) {
                                        Intent intent = new Intent(getActivity(), ShowHistoryActivity.class);
                                        intent.putExtra("RegistrationNumber", registNumber);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getActivity(), AddHistoryActivity.class);
                                        intent.putExtra("RegistrationNumber", registNumber);
                                        startActivity(intent);
                                    }
                                }
                            });
                    }
                    else {
                        speedView.setSpeedAt(0);
                        speedView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getActivity(),AddHistoryActivity.class);
                                intent.putExtra("RegistrationNumber", registNumber);
                                startActivity(intent);
                            }
                        });
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
    public void moveToTroubleshooting() {
        new BottomSheet.Builder(getActivity()).title("Troubleshooting").sheet(R.menu.menu_troublshoot).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.questionnaire:
                        Intent intent = new Intent(getActivity(), TroubleshootActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.obd2:
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.exercise.obd2");
                        if (launchIntent != null) {
                            startActivity(launchIntent);
                        } else {
                            showMessage("There is no package available in android");
                        }
                        break;
                }
            }
        }).show();
    }

    @Override
    public void moveToGuide() {
        new BottomSheet.Builder(getActivity()).title("Car Guide").sheet(R.menu.menu_guide).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.interior:
                        Intent intent = new Intent(getActivity(), InternalGuideActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.exterior:
                        Intent intent2 = new Intent(getActivity(), ExternalGuideActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.engine:
                        Intent intent3 = new Intent(getActivity(), EngineGuideActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        }).show();
    }

    @Override
    public void moveToJournal() {
        new BottomSheet.Builder(getActivity()).title("Reminder").sheet(R.menu.menu_journal).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.add:
                        Intent intent = new Intent(getActivity(), AddReminderActivity.class);
                        intent.putExtra("RegistrationNumber", registNumber);
                        startActivity(intent);
                        break;
                    case R.id.show:
                        Intent intent2 = new Intent(getActivity(), ReminderActivity.class);
                        intent2.putExtra("RegistrationNumber", registNumber);
                        startActivity(intent2);
                        break;
                }
            }
        }).show();
    }

    @Override
    public void moveToNotifications() {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
        startActivity(intent);
    }

    @Override
    public void moveToSignIn() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showMenu() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("About CAR")
                .setIcon(R.drawable.car)
                .setMessage("CAR is an app designed and developed by Haider Ali Babar and Osama Safdar.\n" +
                        "It is a mobile application that is based on android platform that help its users to get assistance" +
                        " in an intelligent manner, in the case their car breaks down, or suffers from any technical issue anytime anywhere." +
                        "It also gives the option for car guide of the car. It will also track health of the car by simple inputs from user." +
                        "All the user needs to do is, download the app.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }
}
