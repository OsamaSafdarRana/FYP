package com.exercise.caraugmentedreality.View.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exercise.caraugmentedreality.Adapter.NotificationAdapter;
import com.exercise.caraugmentedreality.Contract.NotificationContract;
import com.exercise.caraugmentedreality.Model.ListItem;
import com.exercise.caraugmentedreality.Presenter.NotificationPresenter;
import com.exercise.caraugmentedreality.R;
import com.exercise.caraugmentedreality.View.Activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NotificationFragment extends BaseFragment implements NotificationContract.View {

    private NotificationPresenter mPresenter;

    @BindView(R.id.recyclerview_notifications)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    public NotificationFragment() {
        mPresenter = new NotificationPresenter(this);
    }

    @Override
    protected View onPreStart(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification,container,false);
    }

    @Override
    protected void onPostStart(Bundle savedInstanceState) {
        super.onPostStart(savedInstanceState);
        if(savedInstanceState == null){
            listItems = new ArrayList<>();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


            for (int i =0;i<11;i++){
                ListItem listItem = new ListItem(
                        "Title " + (i+1),
                        "Description dummy details...."
                );
                listItems.add(listItem);
            }
            adapter = new NotificationAdapter(listItems,getActivity());
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void openNotification() {

    }
}
