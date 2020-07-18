package com.exercise.caraugmentedreality.View.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.exercise.caraugmentedreality.R;

import butterknife.BindView;

public class TroublshootOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troublshoot_options);

        ImageView iv_car = findViewById(R.id.iv_car);
        Button bt_questionnaire = findViewById(R.id.bt_questionnaire);
        Button bt_obd = findViewById(R.id.bt_obd);
        Button bt_help1 = findViewById(R.id.bt_help1);
        Button bt_help2 = findViewById(R.id.bt_help2);

        bt_questionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToQuestionnaire();
            }
        });
        bt_obd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToObd();
            }
        });
        bt_help1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHelp1();
            }
        });
        bt_help2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHelp2();
            }
        });
        iv_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToHome();
            }
        });
    }

    public void moveToQuestionnaire() {
        Intent intent = new Intent(this, TroubleshootActivity.class);
        startActivity(intent);
    }

    public void moveToObd() {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.exercise.obd2");
        if (launchIntent != null) {
            startActivity(launchIntent);
        } else {
            Toast.makeText(this, "There is no package available in android", Toast.LENGTH_LONG).show();
        }
    }

    public void moveToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @SuppressLint("ResourceType")
    public void moveToHelp1() {
        new AlertDialog.Builder(this,R.style.DialogStyle)
                .setTitle("Questionnaire")
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

    public void moveToHelp2() {
        new AlertDialog.Builder(this,R.style.DialogStyle)
                .setTitle("OBD-2")
                .setMessage("User can troubleshoot any basic problem through OBD-2 Device in case of emergency. Basic problems which" +
                        "are covered are engine heating beacuse of radiator, engine head, engine coolant and battery.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })
                .show();
    }
}