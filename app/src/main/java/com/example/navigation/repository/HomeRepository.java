package com.example.navigation.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.navigation.DAO.TaskDAO;
import com.example.navigation.DAO.TaskDatabase;
import com.example.navigation.ui.home.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeRepository {

    private static HomeRepository instance;
    private final TaskDAO taskDao;
    private final LiveData<List<Task>> allTasks;
    private MutableLiveData<List<Task>> tasks;
    private MutableLiveData<List<Task>> deadlines;
    private MutableLiveData<String> day;
    private final ExecutorService executorService;

    private HomeRepository(Application application){
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        //allTasks= new MutableLiveData<>();
        tasks = new MutableLiveData<>();
        deadlines = new MutableLiveData<>();
        day = new MutableLiveData<>();
        executorService = Executors.newFixedThreadPool(2);

        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date dateToday = new Date();
        String dateNow = formatter.format(dateToday);
        day.setValue(dateNow);

        //Task task1 = new Task("do this thing today", true, "21-04-16", "11:30");
        //Task task2 = new Task("also do this thing today", true, "21-04-16", "11:40");
        //Task task3 = new Task("lastly do this thing today", false, "21-04-17", "11:50");
        //insert(task1);
        //insert(task2);
        //insert(task3);

        allTasks = taskDao.getAllTasks();
        //ArrayList<Task> taskToput = new ArrayList<>();
        //taskToput.add(task1);
        //taskToput.add(task2);
        //taskToput.add(task3);
        //allTasks.setValue(taskToput);
    }

    public static HomeRepository getInstance(Application application){
        if(instance == null)
            instance = new HomeRepository(application);

        return instance;
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public LiveData<String> getDay() {
        return day;
    }

    public void goToDay(String date){
        day.setValue(date);
    }

    public LiveData<List<Task>> getTasks() {
        setTaskList();
        return tasks;
    }

    public LiveData<List<Task>> getDeadlines() {
        setDeadlinesList();
        return deadlines;
    }

    public void insert(Task task) {
        //new InsertTaskAsync(taskDao).execute(task);
        executorService.execute(() -> taskDao.insert(task));
        //List<Task> newTask = allTasks.getValue();
        //newTask.add(task);
        //allTasks.setValue(newTask);
        setTaskList();
    }

    public void removeTask(int position) {
        Task task = tasks.getValue().get(position);

        //List<Task> newTask = allTasks.getValue();
        //newTask.remove(task);
        //allTasks.setValue(newTask);
        executorService.execute(() -> taskDao.delete(task));
        List<Task> tempListtasks = tasks.getValue();
        tempListtasks.remove(task);
        tasks.setValue(tempListtasks);
    }

    private void setTaskList(){
        List<Task> tempList = allTasks.getValue();
        ArrayList<Task> newTempList = new ArrayList<>();
        if(tempList!=null){
            for(int i=0; i< tempList.size(); i++){
                if(tempList.get(i).getDate().matches(day.getValue())){
                    newTempList.add(tempList.get(i));
                }
            }
            sort(newTempList);
            tasks.setValue(newTempList);
        }
    }

    private void setDeadlinesList(){
        List<Task> tempList = allTasks.getValue();
        ArrayList<Task> newTempList = new ArrayList<>();
        if(tempList!=null){
            for(int i=0; i< tempList.size(); i++){
                if(tempList.get(i).isDeadline()){
                    newTempList.add(tempList.get(i));
                }
            }
            sortDeadlines(newTempList);
            deadlines.setValue(newTempList);
        }
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


    /*public LiveData<String> getDay(){
        return homeDao.getDay();
    }
    public LiveData<List<Task>> getTasks(){
        return homeDao.getAllNotes();
    }
    public LiveData<List<Task>> getDeadlines(){
        return homeDao.getDeadlines();
    }
    public void goToDay(String date) {
        homeDao.setDate(date);
    }
    public void insert(Task note) {
        homeDao.insert(note);
    }
    public void removeTask(int position) {
        homeDao.removeTask(position);
    }*/


    private static class InsertTaskAsync extends AsyncTask<Task, Void, Void> {
        private TaskDAO taskDAO;

        private InsertTaskAsync(TaskDAO taskDAO){
            this.taskDAO = taskDAO;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            taskDAO.insert(tasks[0]);
            return null;
        }
    }
}
