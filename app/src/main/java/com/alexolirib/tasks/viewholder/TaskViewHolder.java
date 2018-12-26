package com.alexolirib.tasks.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexolirib.tasks.R;
import com.alexolirib.tasks.entities.TaskEntity;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextDescription;
    private TextView mTextPriorityId;
    private TextView mTextPriority;
    private TextView mTextDueDate;
    private ImageView mImageTask;

    public TaskViewHolder(View itemView) {
        super(itemView);
        this.mTextDescription = itemView.findViewById(R.id.text_description);
    }

    public void bindData(TaskEntity entity) {
        this.mTextDescription.setText(entity.getDescription());
    }
}
