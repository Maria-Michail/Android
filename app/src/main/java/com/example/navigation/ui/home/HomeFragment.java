package com.example.navigation.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import com.example.navigation.R;

import java.util.List;

public class HomeFragment extends Fragment implements HomeAdapter.OnListItemClickListener {

    private HomeViewModel homeViewModel;
    TextView home_date;
    RecyclerView recyclerView;
    HomeAdapter adapter;
    ImageView deleteTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

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

        return root;
    }

    @Override
    public void onClick(int position) {
        final Task task = homeViewModel.getTasks().getValue().get(position);
        homeViewModel.updateTask(task);
        adapter.notifyItemChanged(position);
    }


}