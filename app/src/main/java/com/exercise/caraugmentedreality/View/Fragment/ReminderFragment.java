package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Adapter.NotificationAdapter;
import com.exercise.caraugmentedreality.Contract.ReminderContract;
import com.exercise.caraugmentedreality.Model.Car;
import com.exercise.caraugmentedreality.Model.ListItem;
import com.exercise.caraugmentedreality.Model.Reminder;
import com.exercise.caraugmentedreality.Presenter.ReminderPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.AddReminderActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReminderFragment extends BaseFragment implements ReminderContract.View {

    ReminderPresenter mReminderPresenter;
    @BindView(R.id.bt_back)
    ImageView bt_back;
    @BindView(R.id.bt_continue)
    Button bt_add;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private FirebaseAuth mAuth;
    DatabaseReference reminderRef,userRef;
    String uid,registNumber;

    FirebaseRecyclerAdapter<Reminder, reminderListViewHolder> firebaseRecyclerAdapter;

    public ReminderFragment() {
        mReminderPresenter = new ReminderPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reminder,container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");
            reminderRef = FirebaseDatabase.getInstance().getReference().child("Reminder").child(uid).child(registNumber);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            rv_list.setLayoutManager(linearLayoutManager);

            displayReminders();

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });

            bt_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToAddReminder();
                }
            });
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }

    public void displayReminders() {
        FirebaseRecyclerOptions<Reminder> options =
                new FirebaseRecyclerOptions.Builder<Reminder>()
                        .setQuery(reminderRef, Reminder.class)
                        .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Reminder, reminderListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull reminderListViewHolder holder, int position, @NonNull Reminder model) {
                holder.setServiceType(model.getServiceType());
                holder.setKMs(model.getKMs());
            }

            @NonNull
            @Override
            public reminderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reminder,parent,false);
                reminderListViewHolder viewHolder = new reminderListViewHolder(view);
                return viewHolder;
            }
        };
        firebaseRecyclerAdapter.startListening();
        rv_list.setAdapter(firebaseRecyclerAdapter);
    }
    public static class reminderListViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public reminderListViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setServiceType(String ServiceType) {
            TextView tv_title = mView.findViewById(R.id.tv_reminder_title);
            tv_title.setText(ServiceType);
        }

        public void setKMs(String KMs) {
            TextView tv_description = mView.findViewById(R.id.tv_reminder_description);
            tv_description.setText("Service for car every "+KMs+" KMs");
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
    public void moveToAddReminder() {
        Intent intent = new Intent(getActivity(), AddReminderActivity.class);
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
}


//journalRef = FirebaseDatabase.getInstance().getReference().child("Journal").child(uid);
////
////            try {
////                journalRef.child(registNumber).addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                        if (dataSnapshot.exists()) {
////                            mileage= dataSnapshot.child("SpeedometerReading").getValue().toString();
////                        }
////                    }
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                    }
////                });
////            }catch (NullPointerException NPexc){
////                showMessage(NPexc.toString());
////            }
