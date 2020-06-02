package com.exercise.caraugmentedreality.View.Fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.exercise.caraugmentedreality.Contract.JournalContract;
import com.exercise.caraugmentedreality.Presenter.AddHistoryPresenter;
import com.exercise.caraugmentedreality.Presenter.JournalPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.AddHistoryActivity;
import com.exercise.caraugmentedreality.View.Activity.HealthScoreActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.exercise.caraugmentedreality.View.Activity.JournalActivity;
import com.exercise.caraugmentedreality.View.Activity.ShowHistoryActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

public class JournalFragment extends BaseFragment implements JournalContract.View {

    JournalPresenter mJournalPresenter;

    @BindView(R.id.bt_notification)
    ImageButton bt_notification;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.bt_add)
    Button bt_add;
    @BindView(R.id.bt_show)
    Button bt_show;
    @BindView(R.id.bt_health)
    Button bt_health;
    @BindView(R.id.bt_help1)
    Button bt_help1;
    @BindView(R.id.bt_help2)
    Button bt_help2;
    @BindView(R.id.bt_help3)
    Button bt_help3;

    String registNumber;

    public JournalFragment() {
        mJournalPresenter = new JournalPresenter(this);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_journal,container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");
            showMessage(registNumber);

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
            bt_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToAddHistory();
                }
            });
            bt_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToShowHistory();
                }
            });
            bt_health.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHealth();
                }
            });
            bt_help1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToHelp1();
                }
            });
            bt_help2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToHelp2();
                }
            });
            bt_help3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToHelp3();
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
    public void moveToAddHistory() {
        Intent intent = new Intent(getActivity(), AddHistoryActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
        startActivity(intent);
    }

    @Override
    public void moveToShowHistory() {
        Intent intent = new Intent(getActivity(), ShowHistoryActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
        startActivity(intent);
    }

    @Override
    public void moveToHealth() {
        Intent intent = new Intent(getActivity(), HealthScoreActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
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

    @Override
    public void moveToHelp1() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("Add History")
                .setMessage("User can add history of his car. History includes last changed date of radiator fluid, " +
                        "engine oil, engine coolant, transmission fluid and brake oil. User can update when he gets" +
                        " new service.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }

    @Override
    public void moveToHelp2() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("Show History")
                .setMessage("User can view history of his car. History includes last changed date of radiator fluid, " +
                        "engine oil, engine coolant, transmission fluid and brake oil. User can view updated values when he gets" +
                        " new service.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }

    @Override
    public void moveToHelp3() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("Health Score")
                .setMessage("User can view health score of his car. Health score includes specific ratio of each of the fluid" +
                        "(radiator fluid, engine oil, engine coolant, transmission fluid and brake oil). User gets update of " +
                        "his car health if it's critical.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }
}
