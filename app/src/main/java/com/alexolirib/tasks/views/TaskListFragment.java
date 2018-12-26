package com.alexolirib.tasks.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexolirib.tasks.R;
import com.alexolirib.tasks.adapter.TaskListAdapter;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.TaskEntity;
import com.alexolirib.tasks.infra.operation.OperationListener;
import com.alexolirib.tasks.manager.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class TaskListFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private int mFilter;
    private List<TaskEntity> mTaskEntityList;
    private TaskListAdapter mTaskListAdapter;
    private TaskManager mTaskManager;
    private ViewHolder mViewHolder = new ViewHolder();

    public static TaskListFragment newInstance(int filter) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TaskConstants.TASK_FILTER.KEY, filter);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            this.mFilter = getArguments().getInt(TaskConstants.TASK_FILTER.KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Infla o layout
        View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);

        // Incializa as variáveis
        this.mContext = rootView.getContext();
        this.mTaskManager = new TaskManager(this.mContext);

        // Inicializa elementos
        this.mViewHolder.floatAddTask = (FloatingActionButton) rootView.findViewById(R.id.float_add_task);

        // Inicializa eventos
        this.mViewHolder.floatAddTask.setOnClickListener(this);

        // 1 - Obter a recyclerview
        this.mViewHolder.recylerTaskList = (RecyclerView) rootView.findViewById(R.id.recycler_task_list);

        // 2 - Definir adapter passando listagem de itens
        this.mTaskEntityList = new ArrayList<>();
        this.mTaskListAdapter = new TaskListAdapter(this.mTaskEntityList);
        this.mViewHolder.recylerTaskList.setAdapter(mTaskListAdapter);

        // 3 - Definir um layout
        this.mViewHolder.recylerTaskList.setLayoutManager(new LinearLayoutManager(this.mContext));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mTaskEntityList = new ArrayList<>();
        this.mTaskManager.getList(taskLoadedListener());


    }

    private OperationListener taskLoadedListener(){
        return new OperationListener<List<TaskEntity>>(){
            @Override
            public void onSuccess(List<TaskEntity> result) {
                mTaskEntityList.addAll(result);
                mTaskListAdapter = new TaskListAdapter(mTaskEntityList);
                mViewHolder.recylerTaskList.setAdapter(mTaskListAdapter);
                //avisa que mudou
                mTaskListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
            }
        };
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.float_add_task) {
            startActivity(new Intent(this.mContext, TaskFormActivity.class));
        }
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
        private FloatingActionButton floatAddTask;
        private RecyclerView recylerTaskList;
    }
}
