package com.alexolirib.tasks.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexolirib.tasks.entities.TaskEntity;
import com.alexolirib.tasks.viewholder.TaskViewHolder;
import com.alexolirib.tasks.R;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<TaskEntity> mListTaskEntities;

    public TaskListAdapter(List<TaskEntity> taskList) {
        this.mListTaskEntities=taskList;
        String as = "";
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        // Infla o layout da linha e faz uso na listagem
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_task_list, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        TaskEntity entity = this.mListTaskEntities.get(position);
        holder.bindData(entity);
    }

    @Override
    public int getItemCount() {
        return mListTaskEntities.size();
    }

}
