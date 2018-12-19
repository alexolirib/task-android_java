package com.alexolirib.tasks.manager;

import android.content.Context;
import android.os.AsyncTask;

public class PersonManager {

    public PersonManager(Context context) {

    }

    public void create(String name, String email, String password){
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
            }
        };
    }
}
