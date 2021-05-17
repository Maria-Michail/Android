package com.example.navigation.ui.deadlines;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.R;
import com.example.navigation.ui.home.HomeAdapter;
import com.example.navigation.ui.home.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DeadlinesAdapter extends RecyclerView.Adapter<DeadlinesAdapter.ViewHolder>{

    List<Task> tasks;
    DeadlinesAdapter.OnListItemClickListener onListItemClickListener;
    String dateOfToday;

    public DeadlinesAdapter(List<Task> tasks, DeadlinesAdapter.OnListItemClickListener onListItemClickListener){
        this.tasks = tasks;
        this.onListItemClickListener = onListItemClickListener;
    }

    @NonNull
    @Override
    public DeadlinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.deadlines_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadlinesAdapter.ViewHolder holder, int position) {
        holder.deadline_date.setText(tasks.get(position).getDate());
        holder.deadline_item.setText(tasks.get(position).getName());
        Date dateToday = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
        dateOfToday = simpleDateFormat.format(dateToday);
        if(tasks.get(position).getDate().compareTo(dateOfToday)<0){
            holder.deadline_item.setTextColor(Color.parseColor("#ad7b0e"));
        }
        else{
            holder.deadline_item.setTextColor(Color.parseColor("#050401"));
        }
    }

    @Override
    public int getItemCount() {
        if(tasks == null){
            return 0;
        }
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deadline_date;
        TextView deadline_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListItemClickListener.onClick(getAdapterPosition());
                }
            });
            deadline_date = itemView.findViewById(R.id.deadline_date);
            deadline_item = itemView.findViewById(R.id.deadline_item);
        }
    }

    public interface OnListItemClickListener {
        void onClick(int position);
    }

    public void updateData(List<Task> tasks) {
        this.tasks = tasks;
        System.out.println("this is the current size " + tasks.size());
        notifyDataSetChanged();
    }
}
