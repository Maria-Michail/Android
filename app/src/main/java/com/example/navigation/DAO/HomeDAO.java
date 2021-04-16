package com.example.navigation.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.ui.home.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeDAO {

    private static HomeDAO instance;
    private MutableLiveData<List<Task>> allTasks;
    private MutableLiveData<List<Task>> tasks;
    private MutableLiveData<List<Task>> deadlines;
    private MutableLiveData<String> day;


    private HomeDAO() {
        allTasks= new MutableLiveData<>();
        tasks = new MutableLiveData<>();
        deadlines = new MutableLiveData<>();
        day = new MutableLiveData<>();
        List<Task> dataSet = new ArrayList<>();
        dataSet.add(new Task("do this thing today", true, "21-04-10", "11:30"));
        dataSet.add(new Task("also do this thing today", true, "21-04-10", "11:40"));
        dataSet.add(new Task("lastly do this thing today", false, "21-04-11", "11:50"));
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date dateToday = new Date();
        String dateNow = formatter.format(dateToday);
        System.out.println(dateNow);
        day.setValue(dateNow);
        allTasks.setValue(dataSet);
    }

    public static HomeDAO getInstance(){
        if(instance == null) {
            instance = new HomeDAO();
        }
        return instance;
    }

    public LiveData<String> getDay() {
        return day;
    }

    public void setDate(String date){
        day.setValue(date);
    }

    public LiveData<List<Task>> getAllNotes() {
        setTaskList();
        return tasks;
    }

    public LiveData<List<Task>> getDeadlines() {
        setDeadlinesList();
        return deadlines;
    }

    public void insert(Task task) {
        List<Task> currentTasks = allTasks.getValue();
        currentTasks.add(task);
        allTasks.setValue(currentTasks);
        setTaskList();
    }

    private void setTaskList(){
        List<Task> tempList = allTasks.getValue();
        ArrayList<Task> newTempList = new ArrayList<>();
        for(int i=0; i< tempList.size(); i++){
            if(tempList.get(i).getDate().matches(day.getValue())){
                newTempList.add(tempList.get(i));
            }
        }
        sort(newTempList);
        tasks.setValue(newTempList);
    }

    private void setDeadlinesList(){
        List<Task> tempList = allTasks.getValue();
        ArrayList<Task> newTempList = new ArrayList<>();
        for(int i=0; i< tempList.size(); i++){
            if(tempList.get(i).isDeadline()){
                newTempList.add(tempList.get(i));
            }
        }
        sortDeadlines(newTempList);
        System.out.println("this " + newTempList);
        deadlines.setValue(newTempList);
    }

    public static void sort (ArrayList<Task> arrayTask) {
        Task temp;
        for (int i = 0; i < arrayTask.size(); i++) {
            for (int j = i + 1; j < arrayTask.size(); j++) {
                if (arrayTask.get(i).getTime().compareTo(arrayTask.get(j).getTime()) >= 0) {
                    temp = arrayTask.get(i);
                    arrayTask.set(i, arrayTask.get(j));
                    arrayTask.set(j, temp);
                }
            }
        }
    }

    public static void sortDeadlines (ArrayList<Task> arrayTask) {
        Task temp;
        for (int i = 0; i < arrayTask.size(); i++) {
            for (int j = i + 1; j < arrayTask.size(); j++) {
                if (arrayTask.get(i).getDate().compareTo(arrayTask.get(j).getDate()) >= 0) {
                    temp = arrayTask.get(i);
                    arrayTask.set(i, arrayTask.get(j));
                    arrayTask.set(j, temp);
                }
            }
        }
    }

    public void removeTask(int position) {
        Task temp = tasks.getValue().get(position);
        List<Task> tempList = allTasks.getValue();
        tempList.remove(temp);
        allTasks.setValue(tempList);
        List<Task> tempListtasks = tasks.getValue();
        tempListtasks.remove(temp);
        tasks.setValue(tempListtasks);
    }
}
