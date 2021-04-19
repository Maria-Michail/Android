package com.example.navigation.ui.deadlines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.R;
import com.example.navigation.ui.home.HomeAdapter;
import com.example.navigation.ui.home.Task;

import java.util.List;

public class DeadlinesFragment extends Fragment implements DeadlinesAdapter.OnListItemClickListener{

    private DeadlinesViewModel deadlinesViewModel;
    TextView textView;
    RecyclerView recyclerView;
    DeadlinesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deadlinesViewModel =
                new ViewModelProvider(this).get(DeadlinesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_deadlines, container, false);

        textView = root.findViewById(R.id.text_slideshow);
        deadlinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        deadlinesViewModel.getDeadlines().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.updateData(tasks);
            }
        });

        //make recycler view
        recyclerView = root.findViewById(R.id.rv_deadlines);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //all the elements are the same size
        recyclerView.hasFixedSize();

        adapter = new DeadlinesAdapter(deadlinesViewModel.getDeadlines().getValue(),  this);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onClick(int position) {
        Toast.makeText(getContext(),"Position: " + position, Toast.LENGTH_SHORT).show();
    }
}