package com.exercise.caraugmentedreality.View.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.exercise.caraugmentedreality.Contract.CarsContract;
import com.exercise.caraugmentedreality.Model.Car;
import com.exercise.caraugmentedreality.Presenter.CarsPresenter;
import com.exercise.caraugmentedreality.R;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;

public class CarsFragment extends BaseFragment implements CarsContract.View {

    private CarsPresenter mPresenter;
    private FirebaseAuth mAuth;
    String uid,email;
    @BindView(R.id.rv_carslist)
    RecyclerView rv_carsList;
    @BindView(R.id.bt_addcar)
    Button bt_addcar;
    @BindView(R.id.bt_back)
    ImageButton bt_back;

    DatabaseReference userRef,carsRef,journalRef,reminderRef;
    FirebaseRecyclerAdapter<Car, carListViewHolder> firebaseRecyclerAdapter;

    public CarsFragment() {
        mPresenter = new CarsPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        email = mAuth.getCurrentUser().getEmail();

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        carsRef = FirebaseDatabase.getInstance().getReference().child("Cars").child(uid);
        journalRef = FirebaseDatabase.getInstance().getReference().child("Journal").child(uid);
        reminderRef = FirebaseDatabase.getInstance().getReference().child("Reminder").child(uid);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cars, container, false);
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
            bt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moveToHomeScreen();
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

    public void displayAllCars() {
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
                        holder.setEngineCapacity(model.getEngineCapacity());
                        holder.setRegistrationNumber(model.getRegistrationNumber());

                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                final String registNumber = getItem(position).getRegistrationNumber();
                                removeSelectedCar(registNumber);
                                return true;
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

    private void removeSelectedCar(String regNo) {
        Query carQuery = carsRef.child(regNo);
        carQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot carSnapshot: dataSnapshot.getChildren()) {
                    carSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                showMessage("Error: "+ databaseError.toString());
            }
        });

        Query carJournalQuery = journalRef.child(regNo);
        if(!carJournalQuery.equals(null)) {
            carJournalQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot journalSnapshot : dataSnapshot.getChildren()) {
                        journalSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    showMessage("Error: " + databaseError.toString());
                }
            });
        }

        Query carReminderQuery = reminderRef.child(regNo);
        if(!carReminderQuery.equals(null)) {
            carReminderQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot reminderSnapshot : dataSnapshot.getChildren()) {
                        reminderSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    showMessage("Error: " + databaseError.toString());
                }
            });
        }
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
        public void setEngineCapacity(String engineCapacity){
            TextView tv_capacity = mView.findViewById(R.id.tv_car_capacity);
            tv_capacity.setText(engineCapacity);
        }
    }
    @Override
    public void showProgress() {
        showProgress("Please wait...");
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



//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                // Remove item from backing list here
//                removeSelectedCar();
//                firebaseRecyclerAdapter.notifyDataSetChanged();
//            }
//        });
//        itemTouchHelper.attachToRecyclerView(rv_carsList);