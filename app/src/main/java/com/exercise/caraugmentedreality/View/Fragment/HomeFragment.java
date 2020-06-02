package com.exercise.caraugmentedreality.View.Fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.caraugmentedreality.Contract.HomeContract;
import com.exercise.caraugmentedreality.Presenter.GuidePresenter;
import com.exercise.caraugmentedreality.Presenter.HomePresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.GuideActivity;
import com.exercise.caraugmentedreality.View.Activity.HelpActivity;
import com.exercise.caraugmentedreality.View.Activity.JournalActivity;
import com.exercise.caraugmentedreality.View.Activity.LoginActivity;
import com.exercise.caraugmentedreality.View.Activity.MenuActivity;
import com.exercise.caraugmentedreality.View.Activity.NotificationActivity;
import com.exercise.caraugmentedreality.View.Activity.SignupActivity;
import com.exercise.caraugmentedreality.View.Activity.TroubleshootActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    @BindView(R.id.bt_help1)
    Button bt_help1;
    @BindView(R.id.bt_help2)
    Button bt_help2;
    @BindView(R.id.bt_help3)
    Button bt_help3;
    @BindView(R.id.tv_choose_option)
    TextView tvChoose;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference regNo = database.getReference("Cars/"+database.getReference().child("Registration Number"));

    String registNumber;


//    public static final String IS_TS = "IS_TS";

    public HomeFragment() {
        mPresenter = new HomePresenter(this);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if (savedInstanceState == null) {
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");

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
    public void moveToTroubleshooting() {
        Intent intent = new Intent(getActivity(), TroubleshootActivity.class);
        startActivity(intent);
    }

    @Override
    public void moveToGuide() {
        Intent intent = new Intent(getActivity(), GuideActivity.class);
        startActivity(intent);
    }

    @Override
    public void moveToJournal() {
        Intent intent = new Intent(getActivity(), JournalActivity.class);
        intent.putExtra("RegistrationNumber",registNumber);
        startActivity(intent);
    }



    @Override
    public void moveToNotifications() {
        Intent intent = new Intent(getActivity(), NotificationActivity.class);
        startActivity(intent);
    }

    @SuppressLint("ResourceType")
    @Override
    public void moveToHelp1() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("Troubleshooting")
                .setIcon(R.drawable.back)
                .setMessage("User can troubleshoot any basic problem in case of emergency. Basic problems which " +
                        "are covered are engine heating beacuse of radiator, engine head, engine coolant and battery.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
   }

    @Override
    public void moveToHelp2() {
        new AlertDialog.Builder(getActivity(),R.style.DialogStyle)
                .setTitle("Car Journal")
                .setMessage("User can add History, view history and view Health score of his car. History includes" +
                        " date of last change/service of engine oil, transmission fluid, radiator fluid, engine coolant and brake oil.")
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
                .setTitle("Car Guide")
                .setMessage("User can get complete guide of his car. Guide includes interior guide and exterior guide." +
                        " It will help user understand his car better.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }

    @Override
    public void fetchRegNo() {
//        regNo.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String registrationNum = dataSnapshot.getValue().toString();
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    }

    @Override
    public void moveToSignIn() {
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showMenu() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        startActivity(intent);
//        PopupMenu popup = new PopupMenu(getActivity(), (View) view);
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.i3:
//                        FirebaseAuth.getInstance().signOut();
//                        moveToSignIn();
//                        break;
//                    default:
//                        showMessage("Options Coming Soon.");
//                }
//            return true;
//            }
//        });// to implement on click event on items of menu
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_home, popup.getMenu());
//        popup.show();
    }
}
