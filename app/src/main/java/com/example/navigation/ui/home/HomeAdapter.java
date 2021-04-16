package com.example.navigation.ui.home;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigation.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    List<Task> tasks;
    OnListItemClickListener onListItemClickListener;

    public HomeAdapter(List<Task> tasks, OnListItemClickListener onListItemClickListener){
        this.tasks = tasks;
        this.onListItemClickListener = onListItemClickListener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.home_task_time.setText(tasks.get(position).getTime());
        holder.home_task_item.setText(tasks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView home_task_item;
        TextView home_task_time;
        ImageView deleteTask;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_task_item = itemView.findViewById(R.id.home_task_item);
            home_task_time = itemView.findViewById(R.id.home_task_time);
            deleteTask = itemView.findViewById(R.id.delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListItemClickListener.onClick(getAdapterPosition());
                    if (!home_task_item.getPaint().isStrikeThruText()){
                        home_task_item.setPaintFlags(home_task_item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    else {
                        home_task_item.setPaintFlags(home_task_item.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }
                }
            });
            deleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListItemClickListener.onDelete(getAdapterPosition());
                }
            });
        }
    }

    public interface OnListItemClickListener{
        void onClick(int position);
        void onDelete(int position);
    }
}
