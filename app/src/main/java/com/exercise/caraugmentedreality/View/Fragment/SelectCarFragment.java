package com.exercise.caraugmentedreality.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.caraugmentedreality.Contract.SelectCarContract;
import com.exercise.caraugmentedreality.Model.Car;
import com.exercise.caraugmentedreality.Presenter.SelectCarPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.BaseActivity;
import com.exercise.caraugmentedreality.View.Activity.CarRegistrationActivity;
import com.exercise.caraugmentedreality.View.Activity.HomeActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;

public class SelectCarFragment extends BaseFragment implements SelectCarContract.View {

    private SelectCarPresenter mPresenter;

    private FirebaseAuth mAuth;
    DatabaseReference userRef,carsRef;
    String uid,email;

    @BindView(R.id.rv_carslist)
    RecyclerView rv_carsList;

    @BindView(R.id.bt_addcar)
    Button bt_addcar;

    FirebaseRecyclerAdapter<Car, carListViewHolder> firebaseRecyclerAdapter;

    public SelectCarFragment() {
        mPresenter = new SelectCarPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        carsRef = FirebaseDatabase.getInstance().getReference().child("Cars").child(uid);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_select_car, container, false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);

        if(savedInstanceState == null){
            saveToDB();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            rv_carsList.setLayoutManager(linearLayoutManager);

            displayAllCars();

            bt_addcar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToAddCarScreen();
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

    private void displayAllCars() {
        FirebaseRecyclerOptions<Car> options =
                new FirebaseRecyclerOptions.Builder<Car>()
                        .setQuery(carsRef, Car.class)
                        .build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Car, carListViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull carListViewHolder holder, int position, @NonNull Car model) {
                        holder.setManufacturer(model.getManufacturer());
                        holder.setModel(model.getModel());
                        holder.setVariant(model.getVariant());
                        holder.setYear(model.getYear());
                        holder.setRegistrationNumber(model.getRegistrationNumber());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final String registNumber = getItem(position).getRegistrationNumber();
                                // todo intent
                                Intent intent = new Intent(getActivity(),HomeActivity.class);
                                intent.putExtra("RegistrationNumber",registNumber);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public carListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_car,parent,false);
                        carListViewHolder viewHolder = new carListViewHolder(view);
                        return viewHolder;
                    }
                };
        firebaseRecyclerAdapter.startListening();
        rv_carsList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class carListViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public carListViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setManufacturer(String manufacturer) {
            TextView tv_man = mView.findViewById(R.id.tv_car_man);
            tv_man.setText(manufacturer);
        }

        public void setModel(String model) {
            TextView tv_mod = mView.findViewById(R.id.tv_car_model);
            tv_mod.setText(model);
        }

        public void setVariant(String variant) {
            TextView tv_var = mView.findViewById(R.id.tv_car_var);
            tv_var.setText(variant);
        }

        public void setYear(String year) {
            TextView tv_year = mView.findViewById(R.id.tv_car_year);
            tv_year.setText(year);
        }

        public void setRegistrationNumber(String registrationNumber) {
            TextView tv_regNo = mView.findViewById(R.id.tv_car_regno);
            tv_regNo.setText(registrationNumber);
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
    public void fetchData() {

    }

    @Override
    public void saveToDB(){
        userRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap userMap = new HashMap();
                    userMap.put("Email ID",email);
                userRef.child(uid).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()) {
                            showMessage("Data is loaded successfully");
                        }
                        else {
                            showMessage("Error while loading data");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void moveToAddCarScreen() {
        Intent intent = new Intent(getActivity(), CarRegistrationActivity.class);
        startActivity(intent);
    }
}
