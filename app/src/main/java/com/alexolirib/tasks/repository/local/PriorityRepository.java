package com.alexolirib.tasks.repository.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.alexolirib.tasks.constants.DataBaseConstants;
import com.alexolirib.tasks.constants.TaskConstants;
import com.alexolirib.tasks.entities.PriorityEntity;

import java.util.ArrayList;
import java.util.List;

public class PriorityRepository {

    private static PriorityRepository INSTANCE;
    private TaskDataBaseHelper mTaskDataBaseHelper;

    private PriorityRepository(Context context) {
        mTaskDataBaseHelper = new TaskDataBaseHelper(context);
    }

    public static PriorityRepository getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new PriorityRepository(context);
        }
        return INSTANCE;
    }

    public void insert(List<PriorityEntity> list){

        String sql = "insert into "+ DataBaseConstants.PRIORITY.TABLE_NAME + " (" +
                DataBaseConstants.PRIORITY.COLUMNS.ID+ ", "+ DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION+ ") values (?,?)";

        SQLiteDatabase db = this.mTaskDataBaseHelper.getWritableDatabase();

        db.beginTransaction();

        SQLiteStatement statement = db.compileStatement(sql);
        //insere vários em uma transação
        for(PriorityEntity entity: list){
            statement.bindLong(1, entity.getId());
            statement.bindString(2, entity.getDescription());

            statement.executeInsert();
            statement.clearBindings();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
    public void clearData(){
        SQLiteDatabase db = this.mTaskDataBaseHelper.getWritableDatabase();
        db.delete(DataBaseConstants.PRIORITY.TABLE_NAME, null,null);
        db.close();
    }

    public List<PriorityEntity> getList(){
        List<PriorityEntity> list = new ArrayList<>();
        try {
            Cursor cursor;
            SQLiteDatabase db = this.mTaskDataBaseHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from "+ DataBaseConstants.PRIORITY.TABLE_NAME, null);
            if(cursor != null && cursor.getCount()>0){
                while (cursor.moveToNext()){
                    PriorityEntity entity = new PriorityEntity();
                    entity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseConstants.PRIORITY.COLUMNS.ID)));
                    entity.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseConstants.PRIORITY.COLUMNS.DESCRIPTION)));
                    list.add(entity);
                }
            }
            if(cursor!= null){
                cursor.close();
            }

        }catch (Exception e){
            return list;
        }
        return list;
    }
}
