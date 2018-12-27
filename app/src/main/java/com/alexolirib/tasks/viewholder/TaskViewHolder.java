package com.alexolirib.tasks.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexolirib.tasks.R;
import com.alexolirib.tasks.constants.PriorityCacheConstants;
import com.alexolirib.tasks.entities.TaskEntity;

import java.text.SimpleDateFormat;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private TextView mTextDescription;
    private TextView mTextPriorityId;
    private TextView mTextPriority;
    private TextView mTextDueDate;
    private ImageView mImageTask;

    public TaskViewHolder(View itemView) {
        super(itemView);
        this.mTextDescription = itemView.findViewById(R.id.text_description);
        this.mTextPriorityId = itemView.findViewById(R.id.text_priority_id);
        this.mTextPriority = itemView.findViewById(R.id.text_priority);
        this.mTextDueDate = itemView.findViewById(R.id.text_due_date);
        this.mImageTask = itemView.findViewById(R.id.image_task);
    }

    public void bindData(TaskEntity entity) {
        this.mTextDescription.setText(entity.getDescription());
        this.mTextPriorityId.setText(String.valueOf(entity.getPriorityId()));
        this.mTextDueDate.setText(String.valueOf(SIMPLE_DATE_FORMAT.format(entity.getDueDate())));
        this.mTextPriority.setText(PriorityCacheConstants.get(entity.getPriorityId()));

        if(entity.getComplete()){
            this.mImageTask.setImageResource(R.drawable.ic_done);
        }
    }
}
