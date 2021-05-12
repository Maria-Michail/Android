package com.example.navigation.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import com.example.navigation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.OnListItemClickListener {

    private HomeViewModel homeViewModel;
    TextView home_date;
    RecyclerView recyclerView;
    HomeAdapter adapter;
    View root;
    //ImageView deleteTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        root = inflater.inflate(R.layout.fragment_home, container, false);

        root.setBackground(getResources().getDrawable(R.drawable.coral_palm_trees));
        home_date = root.findViewById(R.id.home_date);
        homeViewModel.getDay().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                home_date.setText(s);
            }
        });
        homeViewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.updateData(tasks);
            }
        });

        //make recycler view
        recyclerView = root.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //all the elements are the same size
        recyclerView.hasFixedSize();

        adapter = new HomeAdapter(homeViewModel.getTasks().getValue(), this);
        recyclerView.setAdapter(adapter);

        //delete an item
        SwipeHelper swipeHelper = new SwipeHelper(getActivity()) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#b37670"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                final Task task = homeViewModel.getTasks().getValue().get(pos);
                                homeViewModel.deleteTask(task);
                                adapter.notifyItemRemoved(pos);
                                Snackbar snackbar = Snackbar.make(recyclerView, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                ));
            }
        };
        swipeHelper.attachToRecyclerView(recyclerView);

        //change background from settings
        try {
            homeViewModel.getBackground().observe(getActivity(), message -> {
                setBackground(message.getBody());
            });
        }catch (Exception e){
            System.out.println("No current user for background");
        }

        //go to next day
        final Button goRightButton = root.findViewById(R.id.arrow_right);
        goRightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToOtherDay(1);
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

        //go to previous day
        final Button goLeftButton = root.findViewById(R.id.arrow_left);
        goLeftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToOtherDay(-1);
                Navigation.findNavController(v).navigate(R.id.nav_home);
            }
        });

        return root;
    }

    void setBackground(String message){
        if (message != null){
            if(message.contentEquals("1")){
                root.setBackground(getResources().getDrawable(R.drawable.coral_palm_trees));
            }
            else if(message.contentEquals("2")){
                root.setBackground(getResources().getDrawable(R.drawable.autumn_leaves));
            }
            else if(message.contentEquals("3")){
                root.setBackground(getResources().getDrawable(R.drawable.sakura_tree));
            }
            else if(message.contentEquals("4")){
                root.setBackground(getResources().getDrawable(R.drawable.pink_flowers));
            }
        }
        else {
            root.setBackground(getResources().getDrawable(R.drawable.coral_palm_trees));
        }
    }

    void goToOtherDay(int day){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(home_date.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        String newDate = simpleDateFormat.format(c.getTime());
        homeViewModel.goToDay(newDate);
    }

    @Override
    public void onClick(int position) {
        final Task task = homeViewModel.getTasks().getValue().get(position);
        homeViewModel.updateTask(task);
        adapter.notifyItemChanged(position);
    }


}