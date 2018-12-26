package com.alexolirib.tasks.views;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.alexolirib.tasks.R;
import com.alexolirib.tasks.entities.PriorityEntity;
import com.alexolirib.tasks.entities.TaskEntity;
import com.alexolirib.tasks.infra.operation.OperationListener;
import com.alexolirib.tasks.manager.PriorityManager;
import com.alexolirib.tasks.manager.TaskManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskFormActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener  {

    private ViewHolder mViewHolder = new ViewHolder();
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private PriorityManager mPriorityManager;
    private TaskManager mTaskManager;
    private Context mContext;
    private List<Integer> mListPriorityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);

        this.mPriorityManager = new PriorityManager(this);
        this.mTaskManager = new TaskManager(this);
        this.mListPriorityId = new ArrayList<>();

        // Inicializa variáveis
        this.mViewHolder.editDescription = (EditText) this.findViewById(R.id.edit_description);
        this.mViewHolder.checkComplete = (CheckBox) this.findViewById(R.id.check_complete);
        this.mViewHolder.spinnerPriority = (Spinner) this.findViewById(R.id.spinner_priority);
        this.mViewHolder.buttonDate = (Button) this.findViewById(R.id.button_date);
        this.mViewHolder.buttonSave = (Button) this.findViewById(R.id.button_save);
        this.mViewHolder.progressDialog = new ProgressDialog(this);

        // Atribui eventos
        this.mViewHolder.buttonSave.setOnClickListener(this);
        this.mViewHolder.buttonDate.setOnClickListener(this);
        
        this.loadPriorities();
        mContext = this;
    }

    private void loadPriorities() {
        List<PriorityEntity> list = this.mPriorityManager.getListLocal();

        List<String> listDescription = new ArrayList<>();
        for(PriorityEntity entity: list){
            listDescription.add(entity.getDescription());
            mListPriorityId.add(entity.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listDescription);
        this.mViewHolder.spinnerPriority.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.button_save) {
            this.handleSave();
        } else if (id == R.id.button_date) {
            this.showDatePicker();
        }
    }

    private void handleSave() {
        showLoading(true, getString(R.string.salvando), getString(R.string.salvando_tarefa));
        TaskEntity entity = new TaskEntity();
        try {
            entity.setComplete(this.mViewHolder.checkComplete.isChecked());
            entity.setDueDate(SIMPLE_DATE_FORMAT.parse(this.mViewHolder.buttonDate.getText().toString()));
            //para o index ser referente a prioridade
            entity.setPriorityId(this.mListPriorityId.get( this.mViewHolder.spinnerPriority.getSelectedItemPosition()));

            if(!"".equals(this.mViewHolder.buttonDate.getText().toString())){
                entity.setDescription(this.mViewHolder.editDescription.getText().toString());
            }

            this.mTaskManager.insert(entity, taskSavedListener());

        }catch (Exception e){
            Toast.makeText(this, R.string.UNEXPECTED_ERROR, Toast.LENGTH_SHORT).show();
            showLoading(false, "", "");
        }


    }

    private void showLoading(Boolean show, String title, String message){
        if(show){
            this.mViewHolder.progressDialog.setTitle(title);
            this.mViewHolder.progressDialog.setMessage(message);
            this.mViewHolder.progressDialog.show();
        }else{
            this.mViewHolder.progressDialog.hide();
            this.mViewHolder.progressDialog.dismiss();
        }
    }

    private OperationListener taskSavedListener(){
        return new OperationListener<Boolean>(){
            @Override
            public void onSuccess(Boolean result) {
                Toast.makeText(mContext, R.string.tarefa_incluida_com_sucesso, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                showLoading(false, "","");
            }
        };
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month,dayOfMonth);
        this.mViewHolder.buttonDate.setText(SIMPLE_DATE_FORMAT.format(calendar.getTime()));

    }

    /**
     * ViewHolder
     */

    private void showDatePicker(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this,this, year, month, dayOfMonth).show();
    }

    private static class ViewHolder {
        private EditText editDescription;
        private CheckBox checkComplete;
        private Spinner spinnerPriority;
        private Button buttonDate;
        private Button buttonSave;
        private ProgressDialog progressDialog;
    }
}
