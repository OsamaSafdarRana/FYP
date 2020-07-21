package com.exercise.caraugmentedreality.View.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.exercise.caraugmentedreality.Adapter.NotificationAdapter;
import com.exercise.caraugmentedreality.Contract.NotificationContract;
import com.exercise.caraugmentedreality.Model.ListItem;
import com.exercise.caraugmentedreality.Presenter.NotificationPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.BaseActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NotificationFragment extends BaseFragment implements NotificationContract.View {

    private NotificationPresenter mPresenter;

    @BindView(R.id.bt_back)
    ImageButton bt_back;

    @BindView(R.id.recyclerview_notifications)
    RecyclerView recyclerView;

    private FirebaseAuth mAuth;
    DatabaseReference  notificationRef;

    String uid,registNumber,mileage;

    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    public NotificationFragment() {
        mPresenter = new NotificationPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification,container,false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            registNumber = getActivity().getIntent().getStringExtra("RegistrationNumber");
            notificationRef = FirebaseDatabase.getInstance().getReference().child("Journal").child(uid);

            listItems = new ArrayList<>();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
                }
            });

            try {
                notificationRef.child(registNumber).child("SpeedometerReading").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            mileage = dataSnapshot.getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                    notificationRef.child(registNumber).child("DaysLeft").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (int i =0;i<=dataSnapshot.getChildrenCount();i++){
                                ListItem listItem = new ListItem(
                                        "Oil Change",
                                        "No of Days left: "+dataSnapshot.getValue().toString()+
                                                " for mileage "+ mileage
                                );
                                listItems.add(listItem);
                            }
                            adapter = new NotificationAdapter(listItems,getActivity());
                            recyclerView.setAdapter(adapter);           }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }catch (NullPointerException NPexc){
                showMessage(NPexc.toString());
            }
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
    public void moveToHomeScreen() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().finish();
        startActivity(intent);
    }
}
